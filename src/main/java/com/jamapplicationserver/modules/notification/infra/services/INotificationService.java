/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.util.Set;
import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author amirhossein
 */
public interface INotificationService {
    
    void send(Notification notification);
    
    boolean isDelivered(Notification notification, Recipient recipient);
    
    boolean canBeSent(Notification notification);
    
}
