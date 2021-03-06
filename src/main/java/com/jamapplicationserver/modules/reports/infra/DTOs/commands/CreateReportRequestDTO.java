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
public class CreateReportRequestDTO extends DTOWithAuthClaims {
    
    public final String message;
    public final String reportedEntityId;
    public final String reportType;
    
    public CreateReportRequestDTO(
            String message,
            String reportedEntityId,
            String reportType,
            UniqueEntityId creatorId,
            UserRole creatorRole
    ) {
        super(creatorId, creatorRole);
        this.message = message;
        this.reportedEntityId = reportedEntityId;
        this.reportType = reportType;
    }
    
}
