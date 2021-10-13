/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.*;
import javax.persistence.*;
import org.hibernate.envers.*;

/**
 *
 * @author dada
 */
@Entity
@Table(name="reports", schema="jamschema")
@Audited
public class ReportModel extends EntityModel {
    
    @Column(name="message", nullable=false, updatable=false)
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private ReportStatusEnum status;
    
    @Column(name="processor_note", nullable=true)
    private String processorNote;
    
    @Column(name="assigned_at", nullable=true)
    private LocalDateTime assignedAt;
    
    @Column(name="processed_at", nullable=true)
    private LocalDateTime processedAt;
    
    @Column(name="archived_at", nullable=true)
    private LocalDateTime archivedAt;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false)
    private LocalDateTime lastModifiedAt;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="reported_entity_id")
    private LibraryEntityModel reportedEntity;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="reporter_id")
    private UserModel reporter;
    
    @ManyToOne(optional=true)
    @JoinColumn(name="processor_id")
    private UserModel processor;

    public ReportModel() {
        
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public ReportStatusEnum getStatus() {
        return this.status;
    }
    
    public void setStatus(ReportStatusEnum status) {
        this.status = status;
    }
    
    public String getProcessorNote() {
        return this.processorNote;
    }
    
    public void setProcessorNote(String processorNote) {
        this.processorNote = processorNote;
    }
    
    public LocalDateTime getAssignedAt() {
        return this.assignedAt;
    }
    
    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
    
    public LocalDateTime getProcessedAt() {
        return this.processedAt;
    }
    
    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
    
    public LocalDateTime getArchivedAt() {
        return this.archivedAt;
    }
    
    public void setArchivedAt(LocalDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
    
    public LibraryEntityModel getReportedEntity() {
        return this.reportedEntity;
    }
    
    public void setReportedEntity(LibraryEntityModel reportedEntity) {
        this.reportedEntity = reportedEntity;
    }
    
    public UserModel getReporter() {
        return this.reporter;
    }
    
    public void setReporter(UserModel reporter) {
        this.reporter = reporter;
    }
    
    public UserModel getProcessor() {
        return this.processor;
    }
    
    public void setProcessor(UserModel processor) {
        this.processor = processor;
    }
    
}
