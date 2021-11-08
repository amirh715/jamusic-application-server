/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.envers.*;

/**
 *
 * @author dada
 */
@Entity
@Audited
@Table(name="notification_recipients", schema="jamschema")
public class NotificationDeliveryModel implements Serializable {
    
    @EmbeddedId
    private NotificationDeliveryId id;
    
    @ManyToOne
    @MapsId("notification_id")
    private NotificationModel notification;
    
    @ManyToOne
    @MapsId("recipient_id")
    private UserModel recipient;
    
    @Column(name="is_delivered")
    private boolean isDelivered;
    
    @Column(name="delivered_at")
    private LocalDateTime deliveredAt;
    
    public NotificationDeliveryModel() {
        
    }
    
    public NotificationDeliveryModel(
            NotificationModel notification,
            UserModel recipient,
            boolean isDelivered,
            LocalDateTime deliveredAt
    ) {
        this.id = new NotificationDeliveryId(notification.getId(), recipient.getId());
        this.notification = notification;
        this.recipient = recipient;
    }
    
    public NotificationDeliveryId getId() {
        return this.id;
    }
    
    public void setId(NotificationDeliveryId id) {
        this.id = id;
    }
    
    public boolean isDelivered() {
        return this.isDelivered;
    }
    
    public void setIsDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }
    
    public LocalDateTime getDeliveredAt() {
        return this.deliveredAt;
    }
    
    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }
    
    public NotificationModel getNotification() {
        return this.notification;
    }
    
    public void setNotification(NotificationModel notification) {
        this.notification = notification;
    }
    
    public UserModel getRecipient() {
        return this.recipient;
    }
    
    public void setRecipient(UserModel recipient) {
        this.recipient = recipient;
    }
    
}
