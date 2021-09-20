/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.Services;

import com.jamapplicationserver.modules.notification.infra.Services.INotificationService;
import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author dada
 */
public class SMSService implements INotificationService {
    
    // fields
    
    private SMSService() {
    }
    
    @Override
    public void send(Notification notification) throws Exception {
        
        final boolean isSent =
                notification.isSent();
        if(isSent) return;
        
        final StringBuilder builder = new StringBuilder();
        
        final String header = "جم";
        final String message = notification.getMessage().getValue();
        final String route =
                notification.getRoute() != null ?
                notification.getRoute().toString() : null;

        final boolean isOnSchedule =
                notification.isOnSchedule();
        if(!isOnSchedule) return;

        final String messageToSend =
                builder
                .append(header)
                .append("\n")
                .append(message)
                .append("\n")
                .append(route)
                .toString();
        
        
        
    }
    
    @Override
    public boolean canSend(Notification notification) throws Exception {

        
        
        return false;
    }
    
    // sms-service specific methods
    
    public static SMSService getInstance() {
        return SMSServiceHolder.INSTANCE;
    }
    
    private static class SMSServiceHolder {

        private static final SMSService INSTANCE = new SMSService();
    }
}
