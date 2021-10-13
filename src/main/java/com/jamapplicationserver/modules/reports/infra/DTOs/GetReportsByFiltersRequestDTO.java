/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs;

import java.util.*;
import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class GetReportsByFiltersRequestDTO extends DTOWithAuthClaims {
    
    public final Set<String> statuses;
    
    public final String reporterId;
    public final String processorId;
    public final String reportedEntityId;
    
    public final String assignedAtFrom;
    public final String assignedAtTill;
    
    public final String processedAtFrom;
    public final String processedAtTill;
    
    public final String archivedAtFrom;
    public final String archivedAtTill;
    
    public final String createdAtFrom;
    public final String createdAtTill;
    
    public final String lastModifiedAtFrom;
    public final String lastModifiedAtTill;
    
    public final String isContentReport;
    
    public GetReportsByFiltersRequestDTO(
            String statuses,
            String reporterId,
            String processorId,
            String reportedEntityId,
            String assignedAtFrom,
            String assignedAtTill,
            String processedAtFrom,
            String processedAtTill,
            String archivedAtFrom,
            String archivedAtTill,
            String createdAtFrom,
            String createdAtTill,
            String lastModifiedAtFrom,
            String lastModifiedAtTill,
            String isContentReport,
            UniqueEntityId creatorId,
            UserRole creatorRole
    ) {
        
        super(creatorId, creatorRole);
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.statuses = serializer.deserialize(statuses, new TypeToken<Set<String>>(){});
        this.reporterId = reporterId;
        this.processorId = processorId;
        this.reportedEntityId = reportedEntityId;
        this.assignedAtFrom = assignedAtFrom;
        this.assignedAtTill = assignedAtTill;
        this.processedAtFrom = processedAtFrom;
        this.processedAtTill = processedAtTill;
        this.archivedAtFrom = archivedAtFrom;
        this.archivedAtTill = archivedAtTill;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.isContentReport = isContentReport;
    }
    
}
