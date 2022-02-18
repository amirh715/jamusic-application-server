/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.DTOs.commands;

import com.jamapplicationserver.core.infra.DTOWithAuthClaims;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class GetMyProfileImageRequestDTO extends DTOWithAuthClaims {
    
    public GetMyProfileImageRequestDTO(UniqueEntityId subjectId, UserRole subjectRole) {
        super(subjectId, subjectRole);
    }
    
    
}
