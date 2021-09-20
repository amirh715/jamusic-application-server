/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.CreateReport;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.modules.reports.repository.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.*;
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
            final Result<UniqueEntityId> reporterIdOrError =
                    UniqueEntityId.createFromString(request.reporterId);
            final Result<UniqueEntityId> reportedEntityIdOrError =
                    UniqueEntityId.createFromString(request.reportedEntityId);
            
            if(messageOrError.isFailure) return messageOrError;
            if(reporterIdOrError.isFailure) return reporterIdOrError;
            if(request.reportedEntityId != null && reportedEntityIdOrError.isFailure)
                return reportedEntityIdOrError;
            
            final Message message = messageOrError.getValue();
            final UniqueEntityId reporterId = reporterIdOrError.getValue();
            final UniqueEntityId reportedEntityId =
                    request.reportedEntityId != null ?
                    reportedEntityIdOrError.getValue()
                    : null;
            
            final Reporter reporter = this.repository.fetchReporterById(reporterId);
            if(reporter == null) return Result.fail(new ReporterDoesNotExistError());
            
            ReportedEntity reportedEntity = null;
            if(request.reportedEntityId != null) {
                System.out.println("reported entity id ==> " + reportedEntityId);
                reportedEntity = this.repository.fetchEntityById(reportedEntityId);
                if(reportedEntity == null) return Result.fail(new ReportedEntityDoesNotExistError());
            }
            
            final Result<Report> reportOrError =
                    Report.create(message, reporter, reportedEntity);
            if(reportOrError.isFailure) return reportOrError;
            
            final Report report = reportOrError.getValue();
            
            this.repository.save(report);
            
            return Result.ok(report);
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
