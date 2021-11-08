/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author dada
 */
public interface INotificationService {
    
    /**
     * 
     * @param notification
     * @throws Exception 
     */
    void send(Notification notification) throws Exception;
    
    /**
     * 
     * @param notification
     * @return
     * @throws Exception 
     */
    boolean canSend(Notification notification) throws Exception;
    
    /**
     * 
     * @param notification
     * @param recipient
     * @return 
     */
    boolean isDeliveredToRecipient(Notification notification, Recipient recipient);
    
}
