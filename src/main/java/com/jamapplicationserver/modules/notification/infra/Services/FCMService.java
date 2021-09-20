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
public class FCMService implements INotificationService {
    
    private FCMService() {
    }
    
    @Override
    public void send(Notification notification) throws Exception {
        
        
        
    }
    
    @Override
    public boolean canSend(Notification notification) throws Exception {
        
        
        
        return false;
    }
    
    public static FCMService getInstance() {
        return FCMServiceHolder.INSTANCE;
    }
    
    private static class FCMServiceHolder {

        private static final FCMService INSTANCE = new FCMService();
    }
}
