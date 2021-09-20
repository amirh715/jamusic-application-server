/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.repository;

import java.util.*;
import java.time.*;
import java.util.stream.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class NotificationRepository implements INotificationRepository {
    
    private final EntityManager em;
    
    private NotificationRepository(EntityManager em) {
        this.em = em;
    }
    
    
    @Override
    public Notification fetchById(UniqueEntityId id) {
        
        try {
            
            final NotificationModel model = em.find(NotificationModel.class, id.toValue());

            return NotificationMapper.toDomain(model);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public Set<Notification> fetchByFilters(NotificationFilters filters) {
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<NotificationModel> cq = cb.createQuery(NotificationModel.class);
            final Root<NotificationModel> root = cq.from(NotificationModel.class);
            
            List<Predicate> predicates =
                    buildCriteriaPredicates(
                            cb,
                            cq,
                            root,
                            filters
                    );
            
            cq.where(
                    predicates.toArray(
                            new Predicate[predicates.size()]
                    )
            );
            
            final List<NotificationModel> results =
                    em.createQuery(cq).getResultList();
            
            return results
                    .stream()
                    .map(model -> NotificationMapper.toDomain(model))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            return null;
        }
        
    }
    
    @Override
    public void save(Notification entity) {
        
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            final boolean exists = this.exists(entity.id);
            
            NotificationModel model;
            
            tnx.begin();
            
            if(exists) { // update existing entity
                                
                model = NotificationMapper.toPersistence(entity);
                
                em.merge(model);
                
            } else { // insert new entity
                
                model = NotificationMapper.toPersistence(entity);
                
                em.persist(model);
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
            throw e;
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        try {
            
            return em.find(NotificationModel.class, id.toValue()) != null;
            
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    @Override
    public void remove(Notification notification) {
        
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final NotificationModel model =
                    em.find(NotificationModel.class, notification.getId().toValue());
            
            em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
        }
    }
    
    private static String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    private List<Predicate> buildCriteriaPredicates(
            CriteriaBuilder cb,
            CriteriaQuery cq,
            Root root,
            NotificationFilters filters
    ) {
        
        cq.select(root);
        
        cq.orderBy();
        
        List<Predicate> predicates = new ArrayList<>();
        
        if(filters != null) {
            
            // searchTerm
            if(filters.searchTerm != null) {
                predicates.add(
                        cb.or(
                                cb.like(root.get("title"), toSearchPattern(filters.searchTerm)),
                                cb.like(root.get("message"), toSearchPattern(filters.searchTerm))
                        )
                );
            }
            
            // notification type
            if(filters.type != null) {
                switch(filters.type) {
                    case APP:
                        cq.from(AppNotificationModel.class);
                        break;
                    case FCM:
                        cq.from(FCMNotificationModel.class);
                        break;
                    case SMS:
                        cq.from(SMSNotificationModel.class);
                        break;
                    case EMAIL:
                        cq.from(EmailNotificationModel.class);
                        break;
                }
            }
            
            // is sent
            if(filters.isSent != null) {
                predicates.add(
                        cb.isTrue(root.get("isSent"))
                );
            }
            
            // sender type
            if(filters.senderType != null) {
                predicates.add(
                        cb.equal(root.get("senderType"), filters.senderType)
                );
            }
            
            // createdAt from till
            if(
                    filters.createdAtFrom != null ||
                    filters.createdAtTill != null
            ) {
                final LocalDateTime from =
                        filters.createdAtFrom != null ?
                        filters.createdAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.createdAtTill != null ?
                        filters.createdAtTill.getValue() :
                        LocalDateTime.now();
                predicates.add(
                        cb.between(
                                root.get("createdAt"),
                                from,
                                till
                        )
                );
            }
            
            // lastModifiedAt from till
            if(
                    filters.lastModifiedAtFrom != null ||
                    filters.lastModifiedAtTill != null
            ) {
                final LocalDateTime from =
                        filters.lastModifiedAtFrom != null ?
                        filters.lastModifiedAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.lastModifiedAtTill != null ?
                        filters.lastModifiedAtTill.getValue() :
                        LocalDateTime.now();
                predicates.add(
                        cb.between(
                                root.get("lastModifiedAt"),
                                from,
                                till
                        )
                );
            }
            
            // scheduledOn from till
            if(
                    filters.scheduledOnFrom != null ||
                    filters.schedulesOnTill != null
            ) {
                final LocalDateTime from =
                        filters.scheduledOnFrom != null ?
                        filters.scheduledOnFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.schedulesOnTill != null ?
                        filters.schedulesOnTill.getValue() :
                        LocalDateTime.now();
                predicates.add(
                        cb.between(
                                root.get("scheduledOn"),
                                from,
                                till
                        )
                );
            }
            
        } // if filters != null ENDS
        
        return predicates;
    }
    
    public static NotificationRepository getInstance() {
        return NotificationRepositoryHolder.INSTANCE;
    }
    
    private static class NotificationRepositoryHolder {
        
        final static EntityManager em =
                EntityManagerFactoryHelper
                        .getInstance()
                        .getEntityManager();

        private static final NotificationRepository INSTANCE = new NotificationRepository(em);
    }
}
