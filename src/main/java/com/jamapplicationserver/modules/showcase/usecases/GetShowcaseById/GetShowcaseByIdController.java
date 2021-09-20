/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetShowcaseById;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetShowcaseByIdController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetShowcaseByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final String id = this.req.params("id");
            
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
    
    public static GetShowcaseByIdController getInstance() {
        return GetShowcaseByIdControllerHolder.INSTANCE;
    }
    
    private static class GetShowcaseByIdControllerHolder {

        private static final GetShowcaseByIdController INSTANCE =
                new GetShowcaseByIdController(GetShowcaseByIdUseCase.getInstance());
    }
}
