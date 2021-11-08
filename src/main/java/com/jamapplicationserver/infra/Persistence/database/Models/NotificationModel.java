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
import java.net.URL;
import org.hibernate.envers.*;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="notifications", schema="jamschema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@Audited
public abstract class NotificationModel extends EntityModel {
    
    public NotificationModel() {
        
    }
    
    @Column(name="title", nullable=true)
    private String title;
    
    @Column(name="message", nullable=true)
    private String message;
    
    @Column(name="route", nullable=true)
    private URL route;
    
    @Column(name="is_bulk", nullable=false)
    private boolean isBulk;
    
    @Column(name="is_sent", nullable=false)
    private boolean isSent;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at")
    private LocalDateTime lastModifiedAt;
    
    @Column(name="scheduled_on")
    private LocalDateTime scheduledOn;
    
    @Column(name="sent_at")
    private LocalDateTime sentAt;
    
    @Enumerated
    private NotificationSenderTypeEnum senderType;
    
    @ManyToOne(optional=true)
    private UserModel sender;
    
    @OneToMany(mappedBy="notification", orphanRemoval=true, cascade=CascadeType.ALL)
    @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
    private Set<NotificationDeliveryModel> deliveries = new HashSet<>();
    
    public String getType() {
        if(this instanceof SMSNotificationModel)
            return "SMS";
        if(this instanceof FCMNotificationModel)
            return "FCM";
        if(this instanceof EmailNotificationModel)
            return "EMAIL";
        else
            return "N/A";
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        if(message == null) return;
        if(message.isBlank() || message.isEmpty())
            this.message = null;
        this.message = message;
    }
    
    public URL getRoute() {
        return this.route;
    }
    
    public void setRoute(URL route) {
        this.route = route;
    }
    
    public boolean isBulk() {
        return this.isBulk;
    }
    
    public void setIsBulk(boolean isBulk) {
        this.isBulk = isBulk;
    }

    public boolean isSent() {
        return this.isSent;
    }
    
    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }
    
    public LocalDateTime getSentAt() {
        return this.sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
    
    public LocalDateTime getScheduledOn() {
        return this.scheduledOn;
    }
    
    public void setScheduledOn(LocalDateTime scheduledOn) {
        this.scheduledOn = scheduledOn;
    }
    
    public NotificationSenderTypeEnum getSenderType() {
        return this.senderType;
    }
    
    public void setSenderType(NotificationSenderTypeEnum senderType) {
        this.senderType = senderType;
    }
    
    public UserModel getSender() {
        return this.sender;
    }
    
    public void setSender(UserModel sender) {
        this.sender = sender;
    }
    
    public Set<NotificationDeliveryModel> getDeliveries() {
        return this.deliveries;
    }
    
    private void replaceDeliveries(Set<NotificationDeliveryModel> deliveries) {
        this.deliveries = deliveries;
    }
    
    public void addDelivery(UserModel recipient, boolean isDelivered, LocalDateTime deliveredAt) {
        final NotificationDeliveryModel delivery =
                new NotificationDeliveryModel(this, recipient, isDelivered, deliveredAt);
        deliveries.add(delivery);
        recipient.addNotification(delivery);
    }
    
    public void removeDelivery(UserModel recipient) {
        deliveries.removeIf(delivery -> delivery.getRecipient().equals(recipient));
    }
    
}
