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
public class AppNotifService implements INotificationService {
    
    private AppNotifService() {
    }
    
    @Override
    public void send(Notification notification) {
        
        
        
    }
    
    @Override
    public boolean canSend(Notification notification) {
        
        
        
        return false;
    }
    
    public static AppNotifService getInstance() {
        return AppNotifServiceHolder.INSTANCE;
    }
    
    private static class AppNotifServiceHolder {

        private static final AppNotifService INSTANCE = new AppNotifService();
    }
}
