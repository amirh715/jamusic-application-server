/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.domain.errors.*;

/**
 *
 * @author dada
 */
public class Report extends AggregateRoot {
    
    private final Message message;
    private ReportStatus status;
    private ReportType type;
    private Message processorNote;
    private DateTime assignedAt;
    private DateTime processedAt;
    private DateTime archivedAt;
    private final Reporter reporter;
    private final ReportedEntity reportedEntity;
    private Processor processor;
    
    // creation constructor
    private Report(
            Message message,
            Reporter reporter,
            ReportedEntity reportedEntity,
            ReportType type
    ) {
        super();
        this.message = message;
        this.reporter = reporter;
        this.reportedEntity = reportedEntity;
        this.status = ReportStatus.PENDING_ASSIGNMENT;
        this.type = type;
    }
    
    // reconstitution constructor
    private Report(
            UniqueEntityId id,
            DateTime createdAt,
            DateTime lastModifiedAt,
            Message message,
            ReportStatus status,
            Message processorNote,
            DateTime assignedAt,
            DateTime processedAt,
            DateTime archivedAt,
            Reporter reporter,
            ReportedEntity reportedEntity,
            ReportType type,
            Processor processor
    ) {
        super(id, createdAt, lastModifiedAt);
        this.message = message;
        this.status = status;
        this.processorNote = processorNote;
        this.assignedAt = assignedAt;
        this.processedAt = processedAt;
        this.archivedAt = archivedAt;
        this.reporter = reporter;
        this.reportedEntity = reportedEntity;
        this.type = type;
        this.processor = processor;
    }
    
    public Message getMessage() {
        return this.message;
    }
    
    public ReportStatus getStatus() {
        return this.status;
    }
    
    public Message getProcessorNote() {
        return this.processorNote;
    }
    
    public DateTime getAssignedAt() {
        return this.assignedAt;
    }
    
    public DateTime getProcessedAt() {
        return this.processedAt;
    }
    
    public DateTime getArchivedAt() {
        return this.archivedAt;
    }
    
    public Reporter getReporter() {
        return this.reporter;
    }
    
    public Processor getProcessor() {
        return this.processor;
    }
    
    public ReportType getReportType() {
        return this.type;
    }
    
    public ReportedEntity getReportedEntity() {
        return this.reportedEntity;
    }
    
    public boolean isTechnicalReport() {
        return this.type.isTechnical();
    }
    
    public boolean isContentReport() {
        return this.type.isContentRelated();
    }
    
    public boolean hasReportedEntity() {
        return this.reportedEntity != null;
    }
    
    public Result markAsAssignedTo(Processor processor) {
        // report has already passed assigning stage
        if(status.isAssigned())
            return Result.fail(new ReportIsAssignedError());
        if(status.isProcessed())
            return Result.fail(new ReportIsProcessedError());
        if(status.isArchived())
            return Result.fail(new ReportIsArchivedError());
        
        if(!hasReportedEntity()) { // general app bug report
            
            // general app bug report must be assigned to the admin
            if(!processor.getRole().isAdmin()) return Result.fail(new AppBugReportsMustBeAssignedToAdminError());
            
        } else { // library entity content report
            
            // library entity content reports must be assigned to the library entity's creator
            final boolean isProcessorTheCreator =
                    this.reportedEntity.getCreatorId().equals(processor.getId());
            if(!isProcessorTheCreator) return Result.fail(new ProcessorMustBeTheEntityCreatorError());
            
        }
        
        this.processor = processor;
        this.status = ReportStatus.ASSIGNED;
        this.assignedAt = DateTime.createNow();
        modified();
        
        return Result.ok();
    }
    
    public Result markAsProcessed(Message processorNote, Processor processor) {
        // report has already passed processing stage
        if(this.status.isPendingAssignment())
            return Result.fail(new ReportIsPendingAssignmentError());
        if(this.status.isProcessed())
            return Result.fail(new ReportIsProcessedError());
        if(this.status.isArchived())
            return Result.fail(new ReportIsArchivedError());
        
        final boolean doesProcessorMatch = this.processor.equals(processor);
        if(!doesProcessorMatch) return Result.fail(new ProcessorMustProcessReportsAssignedToHerError());
        
        this.processorNote = processorNote;
        this.status = ReportStatus.PROCESSED;
        this.processedAt = DateTime.createNow();
        modified();
        
        return Result.ok();
    }
    
    public void archive() {
        if(status.isArchived()) return;
        this.status = ReportStatus.ARCHIVED;
        this.archivedAt = DateTime.createNow();
        modified();
    }

    public static final Result<Report> create(
            Message message,
            Reporter reporter,
            ReportedEntity reportedEntity,
            ReportType type
    ) {
        
        if(message == null) return Result.fail(new ValidationError("پیام گزارش ضروری است"));
        if(reporter == null) return Result.fail(new ValidationError("گزارش دهنده مشخص نیست"));
        if(type == null) return Result.fail(new ValidationError("نوع گزارش مشخص نیست"));
        
        return Result.ok(new Report(message, reporter, reportedEntity, type));
    }
    
    public static final Result<Report> reconstitute(
            UUID id,
            String message,
            String status,
            String processorNote,
            LocalDateTime assignedAt,
            LocalDateTime processedAt,
            LocalDateTime archivedAt,
            Reporter reporter,
            ReportedEntity reportedEntity,
            String type,
            Processor processor,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt
    ) {
        
        final ArrayList<Result> combinedProps = new ArrayList<>();
        
        if(reporter == null) return Result.fail(new ValidationError(""));
        
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<Message> messageOrError = Message.create(message);
        final Result<ReportStatus> statusOrError = ReportStatus.create(status);
        final Result<ReportType> typeOrError = ReportType.create(type);
        final Result<Message> processorNoteOrError = Message.create(processorNote);
        final Result<DateTime> assignedAtOrError = DateTime.create(assignedAt);
        final Result<DateTime> processedAtOrError = DateTime.create(processedAt);
        final Result<DateTime> archivedAtOrError = DateTime.create(archivedAt);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        
        combinedProps.add(idOrError);
        combinedProps.add(messageOrError);
        combinedProps.add(statusOrError);
        combinedProps.add(typeOrError);
        combinedProps.add(createdAtOrError);
        combinedProps.add(lastModifiedAtOrError);
        
        if(processorNote != null) combinedProps.add(processorNoteOrError);
        if(assignedAt != null) combinedProps.add(assignedAtOrError);
        if(processedAt != null) combinedProps.add(processedAtOrError);
        if(archivedAt != null) combinedProps.add(archivedAtOrError);
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        Report instance =
                new Report(
                        idOrError.getValue(),
                        createdAtOrError.getValue(),
                        lastModifiedAtOrError.getValue(),
                        messageOrError.getValue(),
                        statusOrError.getValue(),
                        processorNote != null ? processorNoteOrError.getValue() : null,
                        assignedAt != null ? assignedAtOrError.getValue() : null,
                        processedAt != null ? processedAtOrError.getValue() : null,
                        archivedAt != null ? archivedAtOrError.getValue() : null,
                        reporter,
                        reportedEntity,
                        typeOrError.getValue(),
                        processor
                );
        
        return Result.ok(instance);
        
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Report))
            return false;
        Report r = (Report) obj;
        return r.id.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
}
