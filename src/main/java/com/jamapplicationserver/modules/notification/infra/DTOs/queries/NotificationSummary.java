/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs.queries;

import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class NotificationSummary implements IQueryResponseDTO {
    
    public String id;
    public String title;
    public String type;
    public String isSent;
    public String recipientsCount;
    public String scheduledOn;
    
    public NotificationSummary(
            String id,
            String title,
            String type,
            String isSent,
            String recipientsCount,
            String scheduledOn
    ) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.isSent = isSent;
        this.recipientsCount = recipientsCount;
        this.scheduledOn = scheduledOn;
    }
    
    @Override
    public NotificationSummary filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER: break;
            case SUBSCRIBER: break;
            default:
                return null;
        }
        return this;
    }
    
}
