/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.repository;

import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class NotificationMapper {
    
    public static final Notification toDomain(NotificationModel model) {
        
        Notification instance;
        
        if(model == null) return null;
        
        NotifType type = NotifType.FCM;
        if(model instanceof SMSNotificationModel)
            type = NotifType.SMS;
        if(model instanceof FCMNotificationModel)
            type = NotifType.FCM;
        if(model instanceof EmailNotificationModel)
            type = NotifType.EMAIL;
        
        final Set<NotificationDelivery> deliveries =
                model.getDeliveries()
                .stream()
                .map(d -> {
                    return
                            NotificationDelivery.create(
                                    toDomain(d.getRecipient()),
                                    DateTime.createWithoutValidation(d.getDeliveredAt()),
                                    d.isDelivered()
                            ).getValue();
                })
                .collect(Collectors.toSet());

        final Result<Notification> instanceR = Notification.reconstitute(
                type.getValue(),
                model.getId(),
                model.getTitle(),
                model.getMessage(),
                model.getRoute(),
                model.getSenderType().toString(),
                model.getSentAt(),
                model.isSent(),
                model.getScheduledOn(),
                model.getSender() != null ? model.getSender().getId() : null,
                model.getCreatedAt(),
                model.getLastModifiedAt(),
                deliveries
        );
        if(instanceR.isFailure) System.out.println(instanceR.getError().message);

        return instanceR.getValue();
    }
    
    public static final NotificationModel toPersistence(Notification entity, EntityManager em) {
        
        NotificationModel model;

        if(entity instanceof SMSNotification)
            model = new SMSNotificationModel();
        else if(entity instanceof FCMNotification)
            model = new FCMNotificationModel();
        else if(entity instanceof EmailNotification)
            model = new EmailNotificationModel();
        else
            return null;
        
        model.setId(entity.getId().toValue());
        model.setTitle(
                entity.getTitle() != null ?
                entity.getTitle().getValue() : null
        );
        model.setMessage(
                entity.getMessage() != null ?
                entity.getMessage().getValue() : null
        );
        model.setRoute(
                entity.getRoute() != null ?
                entity.getRoute() : null
        );
        model.setSenderType(
                entity.getSenderType() != null ?
                NotificationSenderTypeEnum.valueOf(entity.getSenderType().getValue())
                        : null
        );
        model.setSender(em.getReference(UserModel.class, entity.getSenderId().toValue()));
        model.setIsBulk(entity.isBulk());
        model.setIsSent(entity.isSent());
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        model.setScheduledOn(entity.getScheduledOn().getValue());
        model.setSentAt(entity.getSentAt() != null ? entity.getSentAt().getValue() : null);
        
        final Set<NotificationDelivery> deliveries = entity.getDeliveries();
        
        for(NotificationDelivery delivery : deliveries) {
            
            final UserModel recipient =
                    em.getReference(UserModel.class, delivery.getRecipient().getId().toValue());

            model.addDelivery(
                    recipient,
                    delivery.isDelivered(),
                    delivery.getDeliveredAt() != null ?
                            delivery.getDeliveredAt().getValue() : null
            );
            
        }
        
        return model;
    }
   
    public static final Recipient toDomain(UserModel model) {
        
        return Recipient.reconstitute(
                model.getId(),
                model.getName(),
                model.getMobile(),
                model.getEmail(),
                model.isEmailVerified(),
                model.getFCMToken(),
                model.getCreatedAt(),
                model.getLastModifiedAt()
        ).getValue();
        
    }
    
}
