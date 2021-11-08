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
public class ProcessReportRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    public final String processorNote;
    
    public ProcessReportRequestDTO(
            String id,
            String processorNote,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        super(updaterId, updaterRole);
        this.id = id;
        this.processorNote = processorNote;
    }
    
}
