/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.infra.services.exceptions.*;

/**
 * Domain service for communicating with FCM, Email, and SMS APIs
 * @author dada
 */
public class NotificationService implements INotificationService {
    
    private NotificationService() {
    }
    
    /**
     * Send notification data to recipients using the proper third-party clients
     * based on notification type
     * @param notification 
     * @throws com.jamapplicationserver.modules.notification.infra.services.exceptions.NotificationCannotBeSentException 
     */
    @Override
    public void send(Notification notification) throws NotificationCannotBeSentException {
        
        try {
            
            if(!notification.isOnSchedule())
                throw new NotificationCannotBeSentException("Notification is not on schedule");
            
            if(notification.isSent())
                throw new NotificationCannotBeSentException("Notification is already sent");
            
            if(!canSend(notification))
                throw new NotificationCannotBeSentException("Third-party won't send this notification");
            
            // call third-party service and send
            final NotifType type = notification.getType();
            switch (type) {
                case FCM:
                    break;
                case SMS:
                    break;
                default:
                    break;
            }
            
            notification.markAsSent();
            
        } catch(NotificationCannotBeSentException e) {
            throw new NotificationCannotBeSentException(e);
        }
        
    }

    /**
     * Check whether the third-party service can send this notification
     * @param notification
     * @return 
     */
    @Override
    public boolean canSend(Notification notification) {
        
        try {
            
            
            
            return true;
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    /**
     * Check whether the notification is delivered by the third-party service to
     * the recipient
     * @param notification
     * @param recipient
     * @return 
     */
    @Override
    public boolean isDeliveredToRecipient(Notification notification, Recipient recipient) {
        
        try {
            
            if(notification.isDelivered(recipient))
                return true;
            
            // call third-party service and check
            
            // notification.markAsDelivered(recipient, deliveredAt);
            
            return false;
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    private void getFCMClient() {
        
    }
    
    private void getEmailClient() {
        
    }
    
    private void getSMSClient() {
        
    }
    
    public static NotificationService getInstance() {
        return NotificationServiceHolder.INSTANCE;
    }
    
    private static class NotificationServiceHolder {

        private static final NotificationService INSTANCE = new NotificationService();
    }
}
