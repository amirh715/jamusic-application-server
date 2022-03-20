/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain.subscribers;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import com.jamapplicationserver.modules.user.domain.events.*;
import com.jamapplicationserver.modules.notification.infra.services.TransactionalSMSService;

/**
 *
 * @author dada
 */
public class AfterAccountVerificationRequest implements IDomainEventHandler<AccountVerificationRequested> {
    
    @Override
    public Class<AccountVerificationRequested> subscribedToEventType() {
        return AccountVerificationRequested.class;
    }
    
    @Override
    public void handleEvent(AccountVerificationRequested event) throws Exception {
                
        try {
            
            final TransactionalSMSService sms = TransactionalSMSService.getInstance();
            
            final int bodyId = 79339;
            final String to = event.user.getMobile().getValue();
            final String code = String.valueOf(event.user.getUserVerification().getCode());
            final String[] args = {code};
            
            sms.send(bodyId, to, args);
            
        } catch(Exception e) {
            LogService.getInstance().fatal(e);
            throw e;
        }
        
    }
    
}
