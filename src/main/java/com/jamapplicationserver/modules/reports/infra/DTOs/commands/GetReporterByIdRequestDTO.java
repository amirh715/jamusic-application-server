/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs.commands;

import com.jamapplicationserver.core.infra.DTOWithAuthClaims;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class GetReporterByIdRequestDTO extends DTOWithAuthClaims {
    
    public String id;
    
    public GetReporterByIdRequestDTO(
            String id,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        this.id = id;
    }
    
}
