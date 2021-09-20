/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.Jobs;

import java.util.*;
import java.time.*;
import org.quartz.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.modules.reports.repository.*;

/**
 *
 * @author dada
 */
public class ArchiveReportsJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        System.out.println("JOB : " + this.getClass().getSimpleName());
        
        final IReportRepository repository = ReportRepository.getInstance();
        
        // reports created 1 year ago or longer
        final DateTime createdAtTill = DateTime.create(LocalDateTime.now()/*.minusYears(1)*/).getValue();
        final ReportFilters olderThanOneYearOldReports =
                new ReportFilters(
                        null,
                        createdAtTill
                );
        
        // fetch reports
        final Set<Report> reports = repository.fetchByFilters(olderThanOneYearOldReports);
        if(reports.isEmpty()) return;

        reports.stream().map(report -> {
            report.archive();
            return report;
        }).forEachOrdered(report -> {
            repository.save(report);
        });
        
    }
    
}
