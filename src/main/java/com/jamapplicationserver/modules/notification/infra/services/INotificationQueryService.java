/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.notification.repository.NotificationFilters;


/**
 *
 * @author dada
 */
public interface INotificationQueryService {
    
    public Set<NotificationDetails> getNotificationsByFilters(NotificationFilters filters);
    
    public NotificationDetails getNotificationById(UniqueEntityId id);
    
    public RecipientDetails getNotificationsOfRecipient(UniqueEntityId recipientId);
    
}
