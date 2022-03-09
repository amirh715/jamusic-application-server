/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs.commands;

import com.jamapplicationserver.core.infra.DTOWithAuthClaims;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class GetNotificationsByFiltersRequestDTO extends DTOWithAuthClaims {
    
    public String searchTerm;
    public String createdAtFrom;
    public String createdAtTill;
    public String lastModifiedAtFrom;
    public String lastModifiedAtTill;
    public String scheduledOnFrom;
    public String scheduledOnTill;
    public String type;
    public String isSent;
    public String withUndeliveredRecipients;
    public String senderType;
    public Integer limit;
    public Integer offset;
    
    public GetNotificationsByFiltersRequestDTO(
            String searchTerm,
            String createdAtFrom,
            String createdAtTill,
            String lastModifiedAtFrom,
            String lastModifiedAtTill,
            String scheduledOnFrom,
            String scheduledOnTill,
            String type,
            String isSent,
            String withUndeliveredRecipients,
            String senderType,
            UniqueEntityId subjectId,
            UserRole subjectRole,
            Integer limit,
            Integer offset
    ) {
        super(subjectId, subjectRole);
        this.searchTerm = searchTerm;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.scheduledOnFrom = scheduledOnFrom;
        this.scheduledOnTill = scheduledOnTill;
        this.type = type;
        this.isSent = isSent;
        this.withUndeliveredRecipients = withUndeliveredRecipients;
        this.senderType = senderType;
        this.limit = limit;
        this.offset = offset;
    }
    
}
