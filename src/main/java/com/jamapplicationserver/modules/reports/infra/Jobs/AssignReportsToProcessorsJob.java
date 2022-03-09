/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.Jobs;

import java.util.*;
import org.quartz.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.modules.reports.repository.*;

/**
 *
 * @author dada
 */
public class AssignReportsToProcessorsJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
                
        final IReportRepository repository = ReportRepository.getInstance();
        
        // pending assignments
        final ReportFilters pendingAssignmentReports =
                new ReportFilters(
                        ReportStatus.PENDING_ASSIGNMENT
                );
        
        // fetch reports
        final Set<Report> reports = repository.fetchByFilters(pendingAssignmentReports);
        if(reports.isEmpty()) return;
        
        reports.forEach(report -> {
            
            Processor processor;
            if(report.hasReportedEntity()) {
                final UniqueEntityId creatorId = report.getReportedEntity().getCreatorId();
                processor = repository.fetchProcessorById(creatorId);
            } else {
                processor = repository.fetchAdminProcessor();
            }
            
            final Result result = report.markAsAssignedTo(processor);
            if(result.isFailure) return;
            
            repository.save(report);
            
        });
        
    }
    
}
