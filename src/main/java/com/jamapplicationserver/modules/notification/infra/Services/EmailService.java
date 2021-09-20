/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.Services;

import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author dada
 */
public class EmailService implements INotificationService {
    
    private EmailService() {
    }
    
    @Override
    public void send(Notification notification) throws Exception {
        
        
        
    }
    
    @Override
    public boolean canSend(Notification notification) throws Exception {
        
        
        
        return false;
    }
    
    public static EmailService getInstance() {
        return EmailServiceHolder.INSTANCE;
    }
    
    private static class EmailServiceHolder {

        private static final EmailService INSTANCE = new EmailService();
    }
}
