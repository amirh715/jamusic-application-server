/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author dada
 */
@Embeddable
public class NotificationDeliveryId implements Serializable {

    private final UUID notification_id;

    private final UUID recipient_id;

    public NotificationDeliveryId(
            UUID notificationId,
            UUID recipientId
    ) {
        this.notification_id = notificationId;
        this.recipient_id = recipientId;
    }
    
    public NotificationDeliveryId() {
        this.notification_id = null;
        this.recipient_id = null;
    }
    
    public UUID getNotificationId() {
        return this.notification_id;
    }
    
    public UUID getRecipientId() {
        return this.recipient_id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((notification_id == null) ? 0 : notification_id.hashCode());
        result = prime * result
                + ((recipient_id == null) ? 0 : recipient_id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        NotificationDeliveryId other = (NotificationDeliveryId) obj;
        return Objects.equals(getNotificationId(), other.getNotificationId())
                &&
                Objects.equals(getRecipientId(), other.getRecipientId());
    }
    
}
