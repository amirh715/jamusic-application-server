/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.RemoveShowcase;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author dada
 */
public class RemoveShowcaseController extends BaseController {
    
    private final IUsecase usecase;
    
    private RemoveShowcaseController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final Result result = this.usecase.execute(fields.get("id"));
            
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
            
            noContent();
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static RemoveShowcaseController getInstance() {
        return RemoveShowcaseControllerHolder.INSTANCE;
    }
    
    private static class RemoveShowcaseControllerHolder {

        private static final RemoveShowcaseController INSTANCE =
                new RemoveShowcaseController(RemoveShowcaseUseCase.getInstance());
    }
}
