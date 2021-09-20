/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.repository;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.reports.domain.*;

/**
 *
 * @author dada
 */
public interface IReportRepository {
    
    public Report fetchById(UniqueEntityId id);
    
    public Reporter fetchReporterById(UniqueEntityId id);
    
    public Processor fetchProcessorById(UniqueEntityId id);
    
    public Processor fetchAdminProcessor();
    
    public ReportedEntity fetchEntityById(UniqueEntityId id);
    
    public Set<Report> fetchByFilters(ReportFilters filters);
    
    public void save(Report report);
    
    public boolean exists(UniqueEntityId id);
    
}
