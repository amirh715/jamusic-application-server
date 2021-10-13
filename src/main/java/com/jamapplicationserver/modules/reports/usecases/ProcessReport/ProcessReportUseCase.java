/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.ProcessReport;

import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.repository.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.modules.reports.domain.errors.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.ProcessReportRequestDTO;

/**
 *
 * @author dada
 */
public class ProcessReportUseCase implements IUsecase<ProcessReportRequestDTO, String> {
    
    private final IReportRepository repository;
    
    private ProcessReportUseCase(IReportRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(ProcessReportRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> reportIdOrError =
                    UniqueEntityId.createFromString(request.id);
            final Result<Message> processorNoteOrError =
                    Message.create(request.processorNote);
            
            final Result[] combinedProps = {
                reportIdOrError,
                processorNoteOrError
            };
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId reportId = reportIdOrError.getValue();
            final Message processorNote = processorNoteOrError.getValue();
            
            final Processor processor = repository.fetchProcessorById(request.subjectId);
            if(processor == null) return Result.fail(new ProcessorDoesNotExistError());
            
            final Report report = repository.fetchById(reportId);
            if(report == null) return Result.fail(new ReportDoesNotExistError());
            
            final Result result = report.markAsProcessed(processorNote, processor);
            if(result.isFailure) return result;
            
            repository.save(report);
            
            return Result.ok(result.getValue());
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static ProcessReportUseCase getInstance() {
        return ProcessReportUseCaseHolder.INSTANCE;
    }
    
    private static class ProcessReportUseCaseHolder {

        private static final ProcessReportUseCase INSTANCE =
                new ProcessReportUseCase(ReportRepository.getInstance());
    }
}
