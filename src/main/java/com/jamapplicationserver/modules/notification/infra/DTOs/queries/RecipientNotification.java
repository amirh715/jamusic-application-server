/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs.queries;

import java.time.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class RecipientNotification implements IQueryResponseDTO {
    
    public String id;
    public String title;
    public String message;
    public String type;
    public String isDelivered;
    public String isSent;
    public String deliveredAt;

    public RecipientNotification(
            String id,
            String title,
            String message,
            String type,
            boolean isSent,
            boolean isDelivered,
            LocalDateTime deliveredAt
    ) {
        this.id = id;
        this.title = title != null ? title : "";
        this.message = message != null ? message : "";
        this.type = type;
        this.isDelivered = Boolean.toString(isDelivered);
        this.isSent = Boolean.toString(isSent);
        this.deliveredAt = deliveredAt != null ? deliveredAt.toString() : null;
    }
    
    @Override
    public RecipientNotification filter(UserRole role) {
        
        return this;
    }
    
    
}
