/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import java.net.URL;
import java.util.*;
import com.jamapplicationserver.core.domain.DateTime;
import com.jamapplicationserver.core.domain.UniqueEntityId;

/**
 *
 * @author dada
 */
public class EmailNotification extends Notification {
    
    // creation constructor
    public EmailNotification(
            NotifTitle title,
            Message message,
            URL route,
            SenderType senderType,
            DateTime scheduledOn,
            UniqueEntityId senderId
    ) {
        super(
                title,
                message,
                route,
                senderType,
                scheduledOn,
                senderId
        );
    }
    
    // reconstitution constructor
    public EmailNotification(
            UniqueEntityId id,
            NotifTitle title,
            Message message,
            URL route,
            SenderType senderType,
            DateTime scheduledOn,
            UniqueEntityId senderId,
            DateTime sentAt,
            boolean isSent,
            DateTime createdAt,
            DateTime lastModifiedAt,
            Map<Recipient, NotificationDelivery> deliveries
    ) {
        super(
                id,
                title,
                message,
                route,
                senderType,
                scheduledOn,
                senderId,
                sentAt,
                isSent,
                createdAt,
                lastModifiedAt,
                deliveries
        );
    }
    
    
}
