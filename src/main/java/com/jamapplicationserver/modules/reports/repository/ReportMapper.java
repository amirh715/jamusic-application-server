/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.repository;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class ReportMapper {
    
    public static final Report toDomain(ReportModel model) {
        
        final Reporter reporter = toReporter(model.getReporter());
        
        ReportedEntity reportedEntity = null;
        if(model.getReportedEntity() != null)
            reportedEntity = toReportedEntity(model.getReportedEntity());
        
        Processor processor = null;
        if(model.getProcessor() != null)
            processor = toProcessor(model.getProcessor());
        
        return Report.reconstitute(
                model.getId(),
                model.getMessage(),
                model.getStatus().toString(),
                model.getProcessorNote(),
                model.getAssignedAt(),
                model.getProcessedAt(),
                model.getArchivedAt(),
                reporter,
                reportedEntity,
                processor,
                model.getCreatedAt(),
                model.getLastModifiedAt()
        ).getValue();
    }
    
    public static final ReportModel toPersistence(Report entity) {
        
        final ReportModel model = new ReportModel();
        
        model.setId(entity.id.toValue());
        model.setMessage(entity.getMessage().getValue());
        model.setStatus(ReportStatusEnum.valueOf(entity.getStatus().getValue()));
        model.setProcessorNote(entity.getProcessorNote() != null ? entity.getProcessorNote().getValue() : null);
        model.setAssignedAt(entity.getAssignedAt() != null ? entity.getAssignedAt().getValue() : null);
        model.setProcessedAt(entity.getProcessedAt() != null ? entity.getProcessedAt().getValue() : null);
        model.setArchivedAt(entity.getArchivedAt() != null ? entity.getArchivedAt().getValue() : null);
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        
        return model;
    }
    
    public static Reporter toReporter(UserModel model) {
        final UniqueEntityId id =
                UniqueEntityId.createFromUUID(model.getId()).getValue();
        final MobileNo mobile =
                model.getMobile() != null ?
                MobileNo.create(model.getMobile()).getValue()
                : null;
        final Email email =
                model.getEmail() != null ?
                Email.create(model.getEmail()).getValue()
                : null;
        final FCMToken fcmToken =
                model.getFCMToken() != null ?
                FCMToken.create(model.getFCMToken()).getValue()
                : null;
        return new Reporter(
                id,
                model.getName(),
                mobile,
                email,
                fcmToken
        );
    }
    
    public static ReportedEntity toReportedEntity(LibraryEntityModel model) {
        final UniqueEntityId id =
                UniqueEntityId.createFromUUID(model.getId()).getValue();
        final ReportedEntityTitle title =
                ReportedEntityTitle.create(model.getTitle()).getValue();
        
        String t = null;
        if(model instanceof BandModel)
            t = "BAND";
        if(model instanceof SingerModel)
            t = "SINGER";
        if(model instanceof AlbumModel)
            t = "ALBUM";
        if(model instanceof TrackModel)
            t = "TRACK";
        final ReportedEntityType type =
                ReportedEntityType.create(t).getValue();
        
        final UniqueEntityId creatorId =
                UniqueEntityId.createFromUUID(model.getCreator().getId()).getValue();
        
        return new ReportedEntity(
                id,
                title,
                type,
                creatorId
        );
    }
    
    public static Processor toProcessor(UserModel model) {
        final UniqueEntityId id =
                UniqueEntityId.createFromUUID(model.getId()).getValue();
        final MobileNo mobile =
                model.getMobile() != null ?
                MobileNo.create(model.getMobile()).getValue()
                : null;
        final Email email =
                model.getEmail() != null ?
                Email.create(model.getEmail()).getValue()
                : null;
        final FCMToken fcmToken =
                model.getFCMToken() != null ?
                FCMToken.create(model.getFCMToken()).getValue()
                : null;
        final UserRole role =
                UserRole.create(model.getRole().toString()).getValue();
        return new Processor(
                id,
                model.getName(),
                role,
                mobile,
                email,
                fcmToken
        );
    }
    
}
