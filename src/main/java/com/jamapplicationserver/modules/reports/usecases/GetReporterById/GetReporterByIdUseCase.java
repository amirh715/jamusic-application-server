/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReporterById;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.reports.infra.services.*;
import com.jamapplicationserver.modules.reports.domain.errors.*;

/**
 *
 * @author dada
 */
public class GetReporterByIdUseCase
        implements IUsecase<GetReporterByIdRequestDTO, ReporterDetails> {
    
    private IReportQueryService queryService;
    
    private GetReporterByIdUseCase(IReportQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<ReporterDetails> execute(GetReporterByIdRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError =
                    UniqueEntityId.createFromString(request.id);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
            
            final ReporterDetails reporter = queryService.getReporterById(id);
            if(reporter == null)
               return Result.fail(new ReporterDoesNotExistError());
            
            return Result.ok(reporter);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetReporterByIdUseCase getInstance() {
        return GetReporterByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetReporterByIdUseCaseHolder {

        private static final GetReporterByIdUseCase INSTANCE =
                new GetReporterByIdUseCase(ReportQueryService.getInstance());
    }
}
