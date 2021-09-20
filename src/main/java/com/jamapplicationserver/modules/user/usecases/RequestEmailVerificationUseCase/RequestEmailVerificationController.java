/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestEmailVerificationUseCase;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author amirhossein
 */
public class RequestEmailVerificationController extends BaseController {
    
    private final IUsecase useCase;
    
    private RequestEmailVerificationController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("RequestEmailVerificationController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(fields.get("id"));
        
        if(idOrError.isFailure) {
            this.clientError(idOrError.getError());
            return;
        }
        
        try {
            
            final Result result = this.useCase.execute(idOrError.getValue());
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                if(error instanceof ConflictError)
                    this.conflict(error);
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                return;
            }
            
            this.noContent();
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static RequestEmailVerificationController getInstance() {
        return RequestEmailVerificationControllerHolder.INSTANCE;
    }
    
    private static class RequestEmailVerificationControllerHolder {

        private static final RequestEmailVerificationController INSTANCE =
                new RequestEmailVerificationController(RequestEmailVerificationUseCase.getInstance());
    }
}
