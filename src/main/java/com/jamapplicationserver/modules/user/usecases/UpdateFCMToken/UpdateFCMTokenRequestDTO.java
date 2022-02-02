/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.UpdateFCMToken;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.DTOWithAuthClaims;

/**
 *
 * @author dada
 */
public class UpdateFCMTokenRequestDTO extends DTOWithAuthClaims {
    
    public final String fcmToken;
    
    public UpdateFCMTokenRequestDTO(
            String fcmToken,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        this.fcmToken = fcmToken;
    }
    
}
