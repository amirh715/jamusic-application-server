/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain.subscribers;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.user.domain.events.AccountVerificationRequested;

/**
 *
 * @author amirhossein
 */
public class AfterAccountVerificationRequested implements IHandle<AccountVerificationRequested> {
    
    public AfterAccountVerificationRequested() {
        this.setupSubscriptions();
    }
    
    @Override
    public final void setupSubscriptions() {
        
    }
    
    @Override
    public void handle(DomainEvent event) {
        
        // do something...
        
    }
    
}
