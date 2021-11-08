/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.util.*;

/**
 *
 * @author dada
 */
public class FCMDataMessageService implements IFCMDataMessageService {
    
    private FCMDataMessageService() {
    }
    
    @Override
    public void send(Set<String> fcmTokens) {
        
    }
    
    public static FCMDataMessageService getInstance() {
        return FCMDataMessageServiceHolder.INSTANCE;
    }
    
    private static class FCMDataMessageServiceHolder {

        private static final FCMDataMessageService INSTANCE = new FCMDataMessageService();
    }
}
