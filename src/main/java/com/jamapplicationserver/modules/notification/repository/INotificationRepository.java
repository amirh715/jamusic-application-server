/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.repository;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.IRepository;
import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author dada
 */
public interface INotificationRepository extends IRepository<Notification> {
    
    public Set<Notification> fetchByFilters(NotificationFilters filters);
    
    public Set<Recipient> fetchRecipientsByIds(Set<UniqueEntityId> recipientsIds);
    
}
