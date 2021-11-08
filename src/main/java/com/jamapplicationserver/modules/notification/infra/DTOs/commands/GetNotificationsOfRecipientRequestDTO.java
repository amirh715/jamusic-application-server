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
public class GetNotificationsOfRecipientRequestDTO extends DTOWithAuthClaims {
    
    public String recipientId;
    
    public GetNotificationsOfRecipientRequestDTO(
            String recipientId,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        this.recipientId = recipientId;
    }
    
}
