/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.Jobs;

import java.util.*;
import org.quartz.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.repository.*;

/**
 *
 * @author dada
 */
public class InquireNotificationDeliveryJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) {
        
        try {
            
            final INotificationRepository repository =
                    NotificationRepository.getInstance();
            
            final NotificationFilters filters = new NotificationFilters();
            filters.withUndeliveredRecipients = true;
            final Set<Notification> notificationsToInquireDelivery =
                    repository.fetchByFilters(filters);
            
            for(Notification notification : notificationsToInquireDelivery) {
                
                if(!notification.hasRemainingDeliveries()) continue;
                
                
                
            }
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
}
