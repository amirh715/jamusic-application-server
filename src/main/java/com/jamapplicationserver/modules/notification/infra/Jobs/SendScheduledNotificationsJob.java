/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.Jobs;

import java.util.*;
import org.quartz.*;
import com.jamapplicationserver.core.domain.DateTime;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.repository.*;
import com.jamapplicationserver.modules.notification.infra.services.*;

/**
 *
 * @author dada
 */
public class SendScheduledNotificationsJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
                
        try {
            
            final INotificationRepository repository =
                    NotificationRepository.getInstance();
            final INotificationService notificationService =
                    NotificationService.getInstance();
            
            final NotificationFilters filters =
                    new NotificationFilters();
            filters.schedulesOnTill = DateTime.createNow();
            Set<Notification> notificationsToSend =
                    repository.fetchByFilters(filters);
            
            for(Notification notification : notificationsToSend) {
                
                final boolean canBeSent = notificationService.canSend(notification);
                if(!canBeSent) continue;
                if(!notification.isOnSchedule()) continue;
                if(notification.isSent()) continue;
                
                notificationService.send(notification);
                
                repository.save(notification);
                
            }
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
        }
        
    }
    
}
