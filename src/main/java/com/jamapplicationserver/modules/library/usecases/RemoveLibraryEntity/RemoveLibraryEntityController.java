/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.RemoveLibraryEntity;

import java.util.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
/**
 *
 * @author dada
 */
public class RemoveLibraryEntityController extends BaseController {
    
    private final IUseCase usecase;
    
    private RemoveLibraryEntityController(IUseCase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final String id = fields.get("id");
            
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
            
            this.noContent();
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static RemoveLibraryEntityController getInstance() {
        return RemoveLibraryEntityControllerHolder.INSTANCE;
    }
    
    private static class RemoveLibraryEntityControllerHolder {

        private static final RemoveLibraryEntityController INSTANCE =
                new RemoveLibraryEntityController(RemoveLibraryEntityUseCase.getInstance());
    }
}
