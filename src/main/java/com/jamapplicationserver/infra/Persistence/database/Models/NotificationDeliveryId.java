/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author dada
 */
public class NotificationDeliveryId implements Serializable {

    private UUID notification_id;

    private UUID recipient_id;
    
    public NotificationDeliveryId() {
        
    }
    
    public UUID getNotificationId() {
        return this.notification_id;
    }
    
    public void setNotificationId(UUID notification_id) {
        this.notification_id = notification_id;
    }
    
    public UUID getRecipientId() {
        return this.recipient_id;
    }
    
    public void setRecipientId(UUID recipientId) {
        this.recipient_id = recipientId;
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
