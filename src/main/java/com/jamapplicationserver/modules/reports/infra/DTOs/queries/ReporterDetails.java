/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class ReporterDetails implements IQueryResponseDTO {
    
    public String id;
    public String reporterName;
    public Set<ReportDetails> reports;
    public long totalReportsCount;
    public long processedReportsCount;
    
    public ReporterDetails(
            UUID id,
            String reporterName,
            Set<ReportDetails> reports,
            long totalReportsCount,
            long processedReportsCount
    ) {
        this.id = id.toString();
        this.reporterName = reporterName;
        this.reports = reports;
        this.totalReportsCount = totalReportsCount;
        this.processedReportsCount = processedReportsCount;
    }
    
    @Override
    public ReporterDetails filter(UserRole role) {
        
        return this;
    }
    
}
