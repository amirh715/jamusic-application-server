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
    
    public DateTime createdAtFrom;
    public DateTime createdAtTill;
    
    public DateTime lastModifiedAtFrom;
    public DateTime lastModifiedAtTill;
    
    public DateTime scheduledOnFrom;
    public DateTime schedulesOnTill;
    
    public String searchTerm;
    
    public Boolean isSent;
    public Boolean withUndeliveredRecipients;
        
    public NotifType type;
    
    public SenderType senderType;
    
    public Integer limit;
    public Integer offset;
    
    public NotificationFilters() {
        
    }
    
    public NotificationFilters(
            DateTime createdAtFrom,
            DateTime createdAtTill,
            DateTime lastModifiedAtFrom,
            DateTime lastModifiedAtTill,
            DateTime scheduledOnFrom,
            DateTime scheduledOnTill,
            String searchTerm,
            Boolean isSent,
            Boolean withUndeliveredRecipients,
            NotifType type,
            SenderType senderType,
            Integer limit,
            Integer offset
    ) {
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.scheduledOnFrom = scheduledOnFrom;
        this.schedulesOnTill = scheduledOnTill;
        this.searchTerm = searchTerm;
        this.isSent = isSent;
        this.withUndeliveredRecipients = withUndeliveredRecipients;
        this.type = type;
        this.senderType = senderType;
        this.limit = limit;
        this.offset = offset;
    }
    
}
