/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs;

import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class CreateReportRequestDTO implements IDTO {
    
    public final String message;
    public final String reporterId;
    public final String reportedEntityId;
    
    public CreateReportRequestDTO(
            String message,
            String reporterId,
            String reportedEntityId
    ) {
        this.message = message;
        this.reporterId = reporterId;
        this.reportedEntityId = reportedEntityId;
    }
    
}
