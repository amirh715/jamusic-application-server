/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.CreateReport;

import com.jamapplicationserver.modules.reports.infra.DTOs.commands.CreateReportRequestDTO;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.modules.reports.repository.*;
import com.jamapplicationserver.modules.reports.domain.errors.*;

/**
 *
 * @author dada
 */
public class CreateReportUseCase implements IUsecase<CreateReportRequestDTO, Report> {
    
    private final IReportRepository repository;
    
    private CreateReportUseCase(IReportRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(CreateReportRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<Message> messageOrError =
                    Message.create(request.message);
            final Result<UniqueEntityId> reportedEntityIdOrError =
                    UniqueEntityId.createFromString(request.reportedEntityId);
            final Result<ReportType> typeOrError = ReportType.create(request.reportType);
            
            if(messageOrError.isFailure) return messageOrError;
            if(request.reportedEntityId != null && reportedEntityIdOrError.isFailure)
                return reportedEntityIdOrError;
            if(typeOrError.isFailure) return typeOrError;
            
            final Message message = messageOrError.getValue();
            final UniqueEntityId reportedEntityId =
                    request.reportedEntityId != null ?
                    reportedEntityIdOrError.getValue()
                    : null;
            final ReportType type = typeOrError.getValue();
            
            final Reporter reporter = repository.fetchReporterById(request.subjectId);
            if(reporter == null) return Result.fail(new ReporterDoesNotExistError());
            
            ReportedEntity reportedEntity = null;
            if(request.reportedEntityId != null) {
                reportedEntity = repository.fetchEntityById(reportedEntityId);
                if(reportedEntity == null) return Result.fail(new ReportedEntityDoesNotExistError());
            }
            
            final Result<Report> reportOrError =
                    Report.create(message, reporter, reportedEntity, type);
            if(reportOrError.isFailure) return reportOrError;
            
            final Report report = reportOrError.getValue();
            
            repository.save(report);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static CreateReportUseCase getInstance() {
        return CreateReportUseCaseHolder.INSTANCE;
    }
    
    private static class CreateReportUseCaseHolder {

        private static final CreateReportUseCase INSTANCE =
                new CreateReportUseCase(ReportRepository.getInstance());
    }
}
