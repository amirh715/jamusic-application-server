/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReportById;

import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.ReportDetails;

/**
 *
 * @author dada
 */
public class GetReportByIdController extends BaseController {
    
    private final IUsecase<String, ReportDetails> usecase;
    
    private GetReportByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final String id = req.params(":id");
            
            final Result<ReportDetails> result = usecase.execute(id);
            
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
            
            noStore();
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetReportByIdController getInstance() {
        return GetReportByIdControllerHolder.INSTANCE;
    }
    
    private static class GetReportByIdControllerHolder {

        private static final GetReportByIdController INSTANCE =
                new GetReportByIdController(GetReportByIdUseCase.getInstance());
    }
}
