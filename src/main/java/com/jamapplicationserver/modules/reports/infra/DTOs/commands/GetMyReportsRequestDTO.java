/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs.commands;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class GetMyReportsRequestDTO extends DTOWithAuthClaims {
    
    public Integer limit;
    public Integer offset;
    
    public GetMyReportsRequestDTO(
            UniqueEntityId subjectId,
            UserRole subjectRole,
            Integer limit,
            Integer offset
    ) {
        super(subjectId, subjectRole);
        this.limit = limit;
        this.offset = offset;
    }
    
}
