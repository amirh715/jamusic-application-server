/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.util.*;
import java.time.*;
import java.util.stream.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.notification.repository.NotificationFilters;

/**
 *
 * @author dada
 */
public class NotificationQueryService implements INotificationQueryService {
    
    private NotificationQueryService() {
    }
    
    @Override
    public Set<NotificationDetails> getNotificationsByFilters(NotificationFilters filters) {
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                        .createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<NotificationModel> query = builder.createQuery(NotificationModel.class);
            final Root<NotificationModel> root = query.from(NotificationModel.class);
            
            final Join deliveries = (Join) root.fetch("deliveries", JoinType.LEFT);
            final Join recipientOfDeliveries = (Join) deliveries.fetch("recipient", JoinType.LEFT);
            
            final Set<Predicate> predicates = new HashSet<>();
            
            if(filters != null) {
                
                if(filters.searchTerm != null) {
                    final Predicate predicate = builder.or(
                            builder.like(root.get("title"), toSearchPattern(filters.searchTerm)),
                            builder.like(root.get("message"), toSearchPattern(filters.searchTerm))
                    );
                    predicates.add(predicate);
                }
                
                if(filters.createdAtFrom != null || filters.createdAtTill != null) {
                    final LocalDateTime from =
                            filters.createdAtFrom != null ?
                            filters.createdAtFrom.getValue() :
                            LocalDateTime.of(2021, 1, 1, 0, 0);
                    final LocalDateTime till =
                            filters.createdAtTill != null ?
                            filters.createdAtTill.getValue() :
                            LocalDateTime.now();
                    final Predicate predicate = builder.between(root.get("createdAt"), from, till);
                    predicates.add(predicate);
                }
                
                if(filters.lastModifiedAtFrom != null || filters.lastModifiedAtTill != null) {
                    final LocalDateTime from =
                            filters.lastModifiedAtFrom != null ?
                            filters.lastModifiedAtFrom.getValue() :
                            LocalDateTime.of(2021, 1, 1, 0, 0);
                    final LocalDateTime till =
                            filters.lastModifiedAtTill != null ?
                            filters.lastModifiedAtTill.getValue() :
                            LocalDateTime.now();
                    final Predicate predicate = builder.between(root.get("lastModifiedAt"), from, till);
                    predicates.add(predicate);
                }
                
                if(filters.scheduledOnFrom != null || filters.schedulesOnTill != null) {
                    final LocalDateTime from =
                            filters.scheduledOnFrom != null ?
                            filters.scheduledOnFrom.getValue() :
                            LocalDateTime.of(2021, 1, 1, 0, 0);
                    final LocalDateTime till =
                            filters.schedulesOnTill != null ?
                            filters.schedulesOnTill.getValue() :
                            LocalDateTime.now();
                    final Predicate predicate = builder.between(root.get("scheduledOn"), from, till);
                    predicates.add(predicate);
                }

                if(filters.isSent != null) {
                    Predicate predicate;
                    if(filters.isSent)
                        predicate = builder.isTrue(root.get("isSent"));
                    else
                        predicate = builder.isFalse(root.get("isSent"));
                    predicates.add(predicate);
                }
                
                if(filters.type != null) {
                    Predicate predicate;
                    if(filters.type.isFCM())
                        predicate = builder.equal(root.type(), FCMNotificationModel.class);
                    else if(filters.type.isSMS())
                        predicate = builder.equal(root.type(), SMSNotificationModel.class);
                    else if(filters.type.isEmail())
                        predicate = builder.equal(root.type(), EmailNotificationModel.class);
                    else
                        predicate = null;
                    
                    predicates.add(predicate);
                        
                }
                
                if(filters.senderType != null) {
                    Predicate predicate;
                    if(filters.senderType.equals(SenderType.SYS))
                        predicate = builder.equal(root.get("senderType"), NotificationSenderTypeEnum.SYS);
                    else
                        predicate = builder.equal(root.get("senderType"), NotificationSenderTypeEnum.USER);
                    predicates.add(predicate);
                }
                
                if(filters.withUndeliveredRecipients != null) {
                    Predicate predicate;
                    final Expression<Long> undeliveredCount =
                            builder.count(builder.isFalse(deliveries.get("isDelivered")));
                    if(filters.withUndeliveredRecipients)
                        predicate = builder.greaterThan(undeliveredCount, 0L);
                    else
                        predicate = builder.lessThan(undeliveredCount, 0L);
                    predicates.add(predicate);
                }

            }
            
            query.where(
                    builder.and(
                        predicates.toArray(new Predicate[predicates.size()])
                    )
            );
            
            return em.createQuery(query)
//                    .setMaxResults(50)
//                    .setFirstResult(0)
                    .getResultStream()
                    .map(notif -> {
                        final long deliveryCount =
                                notif.getDeliveries()
                                        .stream()
                                        .filter(d -> d.isDelivered())
                                        .count();
                        final long recipientsCount =
                                notif.getDeliveries()
                                        .stream()
                                        .count();
                        final Set<RecipientIdAndTitle> recipients =
                                notif.getDeliveries()
                                .stream()
                                .map(d -> {
                                    final UserModel recipient = d.getRecipient();
                                    final String recipientName =
                                            recipient.getName() + " (" + recipient.getMobile() + ")";
                                    return new RecipientIdAndTitle(
                                            recipient.getId().toString(),
                                            recipientName
                                    );
                                })
                                .collect(Collectors.toSet());
                        return new NotificationDetails(
                                notif.getId(),
                                notif.getTitle(),
                                notif.getMessage(),
                                notif.getRoute(),
                                notif.getType(),
                                notif.isSent(),
                                deliveryCount,
                                recipientsCount,
                                notif.getScheduledOn(),
                                notif.getSender().getId(),
                                notif.getSender().getName(),
                                notif.getCreatedAt(),
                                notif.getLastModifiedAt(),
                                recipients
                        );
                    })
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public NotificationDetails getNotificationById(UniqueEntityId id) {
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .createEntityManager();
        
        try {
            
            final NotificationModel notif =
                    em.find(NotificationModel.class, id.toValue());
            final long deliveryCount = 0;
            final long recipientsCount = 0;
            
            return new NotificationDetails(
                    notif.getId(),
                    notif.getTitle(),
                    notif.getMessage(),
                    notif.getRoute(),
                    notif.getType(),
                    notif.isSent(),
                    deliveryCount,
                    recipientsCount,
                    notif.getScheduledOn(),
                    notif.getSender().getId(),
                    notif.getSender().getName(),
                    notif.getCreatedAt(),
                    notif.getLastModifiedAt(),
                    null
            );
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public RecipientDetails getNotificationsOfRecipient(UniqueEntityId recipientId) {
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .createEntityManager();
        
        try {
            
            final UserModel recipient = em.find(UserModel.class, recipientId.toValue());
            final Set<NotificationDeliveryModel> deliveriesOfRecipient =
                    recipient.getNotifications();
            final Set<RecipientNotification> recipientNotifications =
                    deliveriesOfRecipient
                    .stream()
                    .map(delivery -> {
                        final NotificationModel notif = delivery.getNotification();
                        return new RecipientNotification(
                                notif.getId().toString(),
                                notif.getTitle(),
                                notif.getMessage(),
                                notif.getType(),
                                delivery.isDelivered(),
                                notif.isSent(),
                                delivery.getDeliveredAt()
                        );
                    })
                    .collect(Collectors.toSet());
            
            return new RecipientDetails(
                    recipient.getId(),
                    recipient.getName(),
                    recipientNotifications
            );
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    private static String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    public static NotificationQueryService getInstance() {
        return NotificationQueryServiceHolder.INSTANCE;
    }
    
    private static class NotificationQueryServiceHolder {

        private static final NotificationQueryService INSTANCE = new NotificationQueryService();
    }
}
