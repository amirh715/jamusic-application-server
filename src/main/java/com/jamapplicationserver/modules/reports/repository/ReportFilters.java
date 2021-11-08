/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.repository;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.domain.*;

/**
 *
 * @author dada
 */
public class ReportFilters {
    
    public final ReportStatus status;
    
    public final UniqueEntityId reporterId;
    public final UniqueEntityId processorId;
    public final UniqueEntityId reportedEntityId;
    
    public final ReportType type;
    
    public final DateTime assignedAtFrom;
    public final DateTime assignedAtTill;
    
    public final DateTime processedAtFrom;
    public final DateTime processedAtTill;
    
    public final DateTime archivedAtFrom;
    public final DateTime archivedAtTill;
    
    public final DateTime createdAtFrom;
    public final DateTime createdAtTill;
    
    public final DateTime lastModifiedAtFrom;
    public final DateTime lastModifiedAtTill;
    
    public final Boolean isContentReport;
    public final Boolean isTechnicalReport;
    public final Boolean isLibraryEntityReport;
    
    public ReportFilters(
            ReportStatus status,
            UniqueEntityId reporterId,
            UniqueEntityId processorId,
            UniqueEntityId reportedEntityId,
            ReportType type,
            DateTime assignedAtFrom,
            DateTime assignedAtTill,
            DateTime processedAtFrom,
            DateTime processedAtTill,
            DateTime archivedAtFrom,
            DateTime archivedAtTill,
            DateTime createdAtFrom,
            DateTime createdAtTill,
            DateTime lastModifiedAtFrom,
            DateTime lastModifiedAtTill,
            String isContentReport,
            String isTechnicalReport,
            String isLibraryEntityReport
    ) {
        this.status = status;
        this.reporterId = reporterId;
        this.processorId = processorId;
        this.reportedEntityId = reportedEntityId;
        this.type = type;
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
        this.isContentReport = isContentReport != null ? Boolean.parseBoolean(isContentReport) : null;
        this.isTechnicalReport = isTechnicalReport != null ? Boolean.parseBoolean(isTechnicalReport) : null;
        this.isLibraryEntityReport = isLibraryEntityReport != null ? Boolean.parseBoolean(isLibraryEntityReport) : null;
    }
    
    public ReportFilters(
            ReportStatus status
    ) {
        this.status = status;
        this.reporterId = null;
        this.processorId = null;
        this.reportedEntityId = null;
        this.type = null;
        this.assignedAtFrom = null;
        this.assignedAtTill = null;
        this.processedAtFrom = null;
        this.processedAtTill = null;
        this.archivedAtFrom = null;
        this.archivedAtTill = null;
        this.createdAtFrom = null;
        this.createdAtTill = null;
        this.lastModifiedAtFrom = null;
        this.lastModifiedAtTill = null;
        this.isContentReport = null;
        this.isTechnicalReport = null;
        this.isLibraryEntityReport = null;
    }
    
    public ReportFilters(
            DateTime createdAtFrom,
            DateTime createdAtTill
    ) {
        this.status = null;
        this.reporterId = null;
        this.processorId = null;
        this.reportedEntityId = null;
        this.type = null;
        this.assignedAtFrom = null;
        this.assignedAtTill = null;
        this.processedAtFrom = null;
        this.processedAtTill = null;
        this.archivedAtFrom = null;
        this.archivedAtTill = null;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = null;
        this.lastModifiedAtTill = null;
        this.isContentReport = null;
        this.isTechnicalReport = null;
        this.isLibraryEntityReport = null;
    }
    
}
