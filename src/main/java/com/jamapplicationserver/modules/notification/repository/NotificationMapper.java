/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.repository;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class NotificationMapper {
    
    public static final Notification toDomain(NotificationModel model) {
        
        Notification entity;
        
        NotifType type = null;
        if(model instanceof SMSNotificationModel)
            type = NotifType.SMS;
        if(model instanceof FCMNotificationModel)
            type = NotifType.FCM;
        if(model instanceof EmailNotificationModel)
            type = NotifType.EMAIL;
            
        final NotifTitle title = NotifTitle.create(model.getTitle()).getValue();
        final Message message = Message.create(model.getMessage()).getValue();
        final SenderType senderType =
                SenderType.create(model.getSenderType().toString()).getValue();
        final DateTime scheduledOn = DateTime.create(model.getScheduledOn()).getValue();
        final UniqueEntityId senderId =
                UniqueEntityId.createFromUUID(model.getSender().getId()).getValue();
            
        entity = Notification.create(
                type,
                model.getTitle() != null ? title : null,
                model.getMessage() != null ? message : null,
                model.getRoute(),
                senderType,
                scheduledOn,
                senderId
        ).getValue();
        
        

        return entity;
    }
    
    public static final NotificationModel toPersistence(Notification entity) {
        
        NotificationModel model;

        if(entity instanceof SMSNotification)
            model = new SMSNotificationModel();
        if(entity instanceof FCMNotification)
            model = new FCMNotificationModel();
        if(entity instanceof EmailNotification)
            model = new EmailNotificationModel();
        else
            model = null;
        
        model.setId(entity.getId().toValue());
        model.setTitle(entity.getTitle() != null ? entity.getTitle().getValue() : null);
        model.setMessage(entity.getMessage() != null ? entity.getMessage().getValue() : null);
        model.setRoute(entity.getRoute());
        model.setSenderType(NotificationSenderTypeEnum.valueOf(entity.getSenderType().getValue()));
//        model.setSender();
        model.setIsBulk(entity.isBulk());
        model.setIsSent(entity.isSent());
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        model.setScheduledOn(entity.getScheduledOn().getValue());
        
        final Collection<NotificationDelivery> deliveries =
                entity.getDeliveries()
                .values();
        
        for(NotificationDelivery delivery : deliveries) {
            
            final NotificationRecipientModel recipient =
                    new NotificationRecipientModel();
            
            recipient.setNotification(model);
            model.setSender(null);
            
            recipient.setIsDelivered(delivery.isDelivered());
            recipient.setDeliveredAt(
                    delivery.getDeliveredAt() != null ?
                    delivery.getDeliveredAt().getValue() : null
            );
            
            model.addRecipient(recipient);
            
        }
        
        return model;
    }
    
}
