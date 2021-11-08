/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class RecipientDetails implements IQueryResponseDTO {
    
    public String id;
    public String name;
    public Set<RecipientNotification> notifications;
    
    public RecipientDetails(
            UUID id,
            String name,
            Set<RecipientNotification> notifications
    ) {
        this.id = id.toString();
        this.name = name;
        this.notifications = notifications;
    }
    
    @Override
    public RecipientDetails filter(UserRole role) {
        
        return this;
    }
    
}
