/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class ReportDetails implements IQueryResponseDTO {
    
    public String id;
    public String message;
    public String status;
    public String type;
    public String reporterId;
    public String reporterName;
    public String reportedEntityId;
    public String reportedEntityTitle;
    public String processorId;
    public String processorName;
    public String processorNote;
    public String assignedAt;
    public String processedAt;
    public String archivedAt;
    public String createdAt;
    public String lastModifiedAt;
    
    public ReportDetails(
            UUID id,
            String message,
            ReportStatusEnum status,
            String type,
            UUID reporterId,
            String reporterName,
            UUID reportedEntityId,
            String reportedEntityTitle,
            UUID processorId,
            String processorName,
            String processorNote,
            LocalDateTime assignedAt,
            LocalDateTime processedAt,
            LocalDateTime archivedAt,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt
    ) {
        this.id = id != null ? id.toString() : null;
        this.message = message;
        this.status = status != null ? status.toString() : null;
        this.type = type;
        this.reporterId = reporterId != null ? reporterId.toString() : null;
        this.reporterName = reporterName;
        this.reportedEntityId = reportedEntityId != null ? reportedEntityId.toString() : null;
        this.reportedEntityTitle = reportedEntityTitle;
        this.processorId = processorId != null ? processorId.toString() : null;
        this.processorName = processorName;
        this.processorNote = processorNote;
        this.assignedAt = assignedAt != null ? assignedAt.toString() : null;
        this.processedAt = processedAt != null ? processedAt.toString() : null;
        this.archivedAt = archivedAt != null ? archivedAt.toString() : null;
        this.createdAt = createdAt != null ? createdAt.toString() : null;
        this.lastModifiedAt = lastModifiedAt != null ? lastModifiedAt.toString() : null;
    }
    
    @Override
    public ReportDetails filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER:
                this.reporterId = null;
                this.reporterName = null;
                this.assignedAt = null;
                break;
            case SUBSCRIBER:
                this.id = null;
                this.status = null;
                this.processorId = null;
                this.processorName = null;
                this.processorNote = null;
                this.processedAt = null;
                this.assignedAt = null;
                this.archivedAt = null;
                this.lastModifiedAt = null;
                break;
            default:
                return null;
        }
        return this;
    }
    
}
