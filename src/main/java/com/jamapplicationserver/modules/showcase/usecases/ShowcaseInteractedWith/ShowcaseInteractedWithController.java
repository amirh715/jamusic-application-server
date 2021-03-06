/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.ShowcaseInteractedWith;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author dada
 */
public class ShowcaseInteractedWithController extends BaseController {
    
    private IUsecase usecase;
    
    private ShowcaseInteractedWithController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());

            final Result result = usecase.execute(fields.get("id"));
            
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
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static ShowcaseInteractedWithController getInstance() {
        return ShowcaseInteractedWithControllerHolder.INSTANCE;
    }
    
    private static class ShowcaseInteractedWithControllerHolder {

        private static final ShowcaseInteractedWithController INSTANCE =
                new ShowcaseInteractedWithController(ShowcaseInteractedWithUseCase.getInstance());
    }
}
