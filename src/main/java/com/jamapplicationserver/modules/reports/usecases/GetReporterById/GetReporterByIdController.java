/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReporterById;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.*;

/**
 *
 * @author dada
 */
public class GetReporterByIdController extends BaseController {
    
    private final IUsecase<GetReporterByIdRequestDTO, ReporterDetails> usecase;
    
    private GetReporterByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final GetReporterByIdRequestDTO dto =
                    new GetReporterByIdRequestDTO(
                            req.params("id"),
                            subjectId,
                            subjectRole
                    );
            
            final Result<ReporterDetails> result = usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                if(error instanceof ConflictError)
                    conflict(error);
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                return;    
            }
            
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetReporterByIdController getInstance() {
        return GetReporterByIdControllerHolder.INSTANCE;
    }
    
    private static class GetReporterByIdControllerHolder {

        private static final GetReporterByIdController INSTANCE =
                new GetReporterByIdController(GetReporterByIdUseCase.getInstance());
    }
}
