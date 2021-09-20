/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.repository;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author dada
 */
public class NotificationFilters {
    
    public final DateTime createdAtFrom;
    public final DateTime createdAtTill;
    
    public final DateTime lastModifiedAtFrom;
    public final DateTime lastModifiedAtTill;
    
    public final DateTime scheduledOnFrom;
    public final DateTime schedulesOnTill;
    
    public final DateTime deliveredAtFrom;
    public final DateTime deliveredAtTill;
    
    public final String searchTerm; // title, message, recipient
    
    public final Boolean isSent;
        
    public final NotifType type;
    
    public final SenderType senderType;
    
    public NotificationFilters(
            DateTime createdAtFrom,
            DateTime createdAtTill,
            DateTime lastModifiedAtFrom,
            DateTime lastModifiedAtTill,
            DateTime scheduledOnFrom,
            DateTime scheduledOnTill,
            DateTime deliveredAtFrom,
            DateTime deliveredAtTill,
            String searchTerm,
            Boolean isSent,
            NotifType type,
            SenderType senderType
    ) {
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.scheduledOnFrom = scheduledOnFrom;
        this.schedulesOnTill = scheduledOnTill;
        this.deliveredAtFrom = deliveredAtFrom;
        this.deliveredAtTill = deliveredAtTill;
        this.searchTerm = searchTerm;
        this.isSent = isSent;
        this.type = type;
        this.senderType = senderType;
    }
    
}
