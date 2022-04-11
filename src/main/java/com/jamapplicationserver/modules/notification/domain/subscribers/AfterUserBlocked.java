/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain.subscribers;

import java.util.*;
import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.user.domain.events.*;
import com.jamapplicationserver.modules.notification.infra.services.*;

/**
 *
 * @author dada
 */
public class AfterUserBlocked implements IDomainEventHandler<UserBlocked> {
    
    @Override
    public Class<UserBlocked> subscribedToEventType() {
        return UserBlocked.class;
    }
    
    @Override
    public void handleEvent(UserBlocked event) throws Exception {
        
        try {
                        
            final IFCMDataMessageService fcmService = FCMDataMessageService.getInstance();
            
            final Map<String, String> data = Map.of("COMMAND", "USER_BLOCKED");
            final Set<String> fcmTokens = Set.of(event.user.getFCMToken().getValue());
            
            fcmService.send(data, fcmTokens);
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
}
