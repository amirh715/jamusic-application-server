/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetAllShowcases;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetAllShowcasesController extends BaseController {
    
    private IUsecase usecase;
    
    private GetAllShowcasesController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Result result = this.usecase.execute(null);
            
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
    
    public static GetAllShowcasesController getInstance() {
        return GetAllShowcasesControllerHolder.INSTANCE;
    }
    
    private static class GetAllShowcasesControllerHolder {

        private static final GetAllShowcasesController INSTANCE =
                new GetAllShowcasesController(GetAllShowcasesUseCase.getInstance());
    }
}
