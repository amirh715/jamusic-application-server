/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReportById;

import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.modules.reports.domain.errors.*;
import com.jamapplicationserver.modules.reports.repository.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.ReportDetails;

/**
 *
 * @author dada
 */
public class GetReportByIdUseCase implements IUsecase<String, ReportDetails> {
    
    private final IReportRepository repository;
    
    private GetReportByIdUseCase(IReportRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result<ReportDetails> execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError =
                    UniqueEntityId.createFromString(idString);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Report report =
                    repository.fetchById(id);
            if(report == null)
                return Result.fail(new ReportDoesNotExistError());
            
            return Result.ok(report);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetReportByIdUseCase getInstance() {
        return GetReportByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetReportByIdUseCaseHolder {

        private static final GetReportByIdUseCase INSTANCE =
                new GetReportByIdUseCase(ReportRepository.getInstance());
    }
}
