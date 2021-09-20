/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain.events;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.notification.domain.Notification;

/**
 *
 * @author dada
 */
public class NotificationSentEvent extends DomainEvent {
    
    public NotificationSentEvent(Notification aggregate) {
        super(aggregate);
    }
    
    
}
