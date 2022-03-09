/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.util.*;
import com.google.firebase.messaging.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class FCMDataMessageService implements IFCMDataMessageService {
    
    private FCMDataMessageService() {
    }
    
    @Override
    public void send(Map<String, String>data, Set<String> fcmTokens) {
        
        try {
            
            final MulticastMessage message =
                    MulticastMessage
                    .builder()
                    .putAllData(data)
                    .addAllTokens(fcmTokens)
                    .build();
            
            FirebaseMessaging.getInstance().sendMulticast(message);

        } catch(Exception e) {
            LogService.getInstance().error(e);
        }
        
    }
    
    public static FCMDataMessageService getInstance() {
        return FCMDataMessageServiceHolder.INSTANCE;
    }
    
    private static class FCMDataMessageServiceHolder {

        private static final FCMDataMessageService INSTANCE = new FCMDataMessageService();
    }
}
