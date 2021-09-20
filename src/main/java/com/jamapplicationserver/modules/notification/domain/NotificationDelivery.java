/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class NotificationDelivery extends ValueObject {
    
    private final Recipient recipient;
    private final DateTime deliveredAt;
    private final boolean isDelivered;
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    private NotificationDelivery(
            Recipient recipient,
            DateTime deliveredAt,
            boolean isDelivered
    ) {
        this.recipient = recipient;
        this.deliveredAt = deliveredAt;
        this.isDelivered = isDelivered;
    }
    
    private NotificationDelivery(
            Recipient recipient
    ) {
        this.recipient = recipient;
        this.deliveredAt = null;
        this.isDelivered = false;
    }
    
    public Recipient getRecipient() {
        return this.recipient;
    }
    
    public DateTime getDeliveredAt() {
        return this.deliveredAt;
    }
    
    public boolean isDelivered() {
        return this.isDelivered;
    }
    
    public static final NotificationDelivery create(
            Recipient recipient,
            DateTime deliveredAt,
            boolean isDelivered
    ) {
        return new NotificationDelivery(recipient, deliveredAt, isDelivered);
    }
    
    public static final NotificationDelivery create(Recipient recipient) {
        
        return new NotificationDelivery(recipient);
    }
    
}
