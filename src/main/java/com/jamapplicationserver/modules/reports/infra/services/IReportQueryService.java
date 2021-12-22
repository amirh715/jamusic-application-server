/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.services;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.reports.repository.ReportFilters;

/**
 *
 * @author dada
 */
public interface IReportQueryService {
    
    public Set<ReportDetails> getReportsByFilters(ReportFilters filters);
    
    public Set<ReportDetails> getAllReports(UniqueEntityId subjectId, UserRole role);
    
    public ReportDetails getReportById(UniqueEntityId id);
    
    public ReporterDetails getReporterById(UniqueEntityId reporterId);
    
    public ReportsSummary getReportsSummary();
    
}
