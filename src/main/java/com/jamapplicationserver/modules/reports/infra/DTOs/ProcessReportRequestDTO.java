/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class ProcessReportRequestDTO implements IDTO {
    
    public final String id;
    public final String processorNote;
    public final String processorId;
    
    public ProcessReportRequestDTO(
            String id,
            String processorId,
            String processorNote
    ) {
        this.id = id;
        this.processorId = processorId;
        this.processorNote = processorNote;
    }
    
}
