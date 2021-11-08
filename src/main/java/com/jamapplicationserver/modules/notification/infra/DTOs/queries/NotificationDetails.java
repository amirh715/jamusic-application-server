/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import java.net.URL;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class NotificationDetails implements IQueryResponseDTO {
    
    public String id;
    public String title;
    public String message;
    public URL route;
    public String type;
    public Boolean isSent;
    public Long deliveryCount;
    public Long recipientsCount;
    public LocalDateTime scheduledOn;
    public UUID senderId;
    public String senderName;
    public LocalDateTime createdAt;
    public LocalDateTime lastModifiedAt;
    public Set<RecipientIdAndTitle> recipients;
    
    public NotificationDetails(
            UUID id,
            String title,
            String message,
            URL route,
            String type,
            Boolean isSent,
            Long deliveryCount,
            Long recipientsCount,
            LocalDateTime scheduledOn,
            UUID senderId,
            String senderName,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            Set<RecipientIdAndTitle> recipients
    ) {
        this.id = id.toString();
        this.title = title;
        this.message = message;
        this.route = route;
        this.type = type;
        this.isSent = isSent;
        this.deliveryCount = deliveryCount;
        this.recipientsCount = recipientsCount;
        this.scheduledOn = scheduledOn;
        this.senderId = senderId;
        this.senderName = senderName;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.recipients = recipients;
    }
    
    
    @Override
    public NotificationDetails filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER:
                return null;
            case SUBSCRIBER:
                return null;
            default:
                return null;
        }
        return this;
    }
    
}
