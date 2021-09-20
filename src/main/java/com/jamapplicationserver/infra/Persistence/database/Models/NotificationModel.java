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

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="notifications", schema="jamschema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public abstract class NotificationModel implements Serializable {
    
    public NotificationModel() {
        
    }
    
    @Id
    @Column(name="id")
    private UUID id;
    
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
    
    @Enumerated
    private NotificationSenderTypeEnum senderType;
    
    @ManyToOne(optional=true)
    private UserModel sender;
    
    @OneToMany(mappedBy="notification")
    private Set<NotificationRecipientModel> recipients = new HashSet<>();
    
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
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
    
    public Set<NotificationRecipientModel> getRecipients() {
        return this.recipients;
    }
    
    private void setRecipients(Set<NotificationRecipientModel> recipients) {
        this.recipients = recipients;
    }
    
    public void addRecipient(NotificationRecipientModel recipient) {
        this.recipients.add(recipient);
    }
    
    public void removeRecipient(NotificationRecipientModel recipient) {
        this.recipients.remove(recipient);
    }
    
}
