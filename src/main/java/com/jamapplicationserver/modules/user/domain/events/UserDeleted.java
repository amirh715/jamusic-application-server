/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.events;

import com.jamapplicationserver.core.domain.events.DomainEvent;
import com.jamapplicationserver.modules.user.domain.User;

/**
 *
 * @author amirhossein
 */
public class UserDeleted extends DomainEvent {
    
    public UserDeleted(
            User aggregate
    ) {
        super(aggregate);
    }
    
}
