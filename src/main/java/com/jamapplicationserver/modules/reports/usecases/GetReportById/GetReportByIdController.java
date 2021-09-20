/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReportById;

import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.domain.IUsecase;

/**
 *
 * @author dada
 */
public class GetReportByIdController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetReportByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final String id = this.req.params(":id");
            
            final Result result = this.usecase.execute(id);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                if(error instanceof ConflictError)
                    this.conflict(error);
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
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
