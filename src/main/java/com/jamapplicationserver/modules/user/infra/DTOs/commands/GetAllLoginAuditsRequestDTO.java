/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.DTOs.commands;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.DTOWithAuthClaims;

/**
 *
 * @author dada
 */
public class GetAllLoginAuditsRequestDTO extends DTOWithAuthClaims {
    
    public final Integer limit;
    public final Integer offset;
    
    public GetAllLoginAuditsRequestDTO(
            Integer limit,
            Integer offset,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        this.limit = limit;
        this.offset = offset;
    }
    
}
