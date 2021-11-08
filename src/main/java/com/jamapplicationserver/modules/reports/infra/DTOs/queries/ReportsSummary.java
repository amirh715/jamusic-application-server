/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;

/**
 *
 * @author dada
 */
public class ReportsSummary implements IQueryResponseDTO {
    
    public long totalReportsCount;
    public long processedReportsCount;
    public long assignedReportsCount;
    public long technicalReportsCount;
    public long contentReportsCount;
    public long libraryEntityReportsCount;
    public Set<ReportedLibraryEntityIdAndTitle> mostReportedLibraryEntities;

    public ReportsSummary(
            long totalReportsCount,
            long processedReportsCount,
            long assignedReportsCount,
            long technicalReportsCount,
            long contentReportsCount,
            long libraryEntityReportsCount,
            Set<ReportedLibraryEntityIdAndTitle> mostReportedLibraryEntities
    ) {
        this.totalReportsCount = totalReportsCount;
        this.processedReportsCount = processedReportsCount;
        this.assignedReportsCount = assignedReportsCount;
        this.technicalReportsCount = technicalReportsCount;
        this.contentReportsCount = contentReportsCount;
        this.libraryEntityReportsCount = libraryEntityReportsCount;
        this.mostReportedLibraryEntities = mostReportedLibraryEntities;
    }
    
    public ReportsSummary() {
        
    }
    
    @Override
    public ReportsSummary filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            default:
                return null;
        }
        return this;
    }
    
}
