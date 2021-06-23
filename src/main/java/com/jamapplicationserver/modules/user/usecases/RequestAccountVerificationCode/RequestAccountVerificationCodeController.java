/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestAccountVerificationCode;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;


/**
 *
 * @author amirhossein
 */
public class RequestAccountVerificationCodeController extends BaseController {
    
    private final IUseCase useCase;
    
    @Override
    public void executeImpl() {
        
        System.out.println("RequestUserVerificationRequestController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromString(fields.get("id"));
        
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
    
    private RequestAccountVerificationCodeController(RequestAccountVerificationCodeUseCase useCase) {
        this.useCase = useCase;
    }
    
    public static RequestAccountVerificationCodeController getInstance() {
        return RequestAccountVerificationCodeControllerHolder.INSTANCE;
    }
    
    private static class RequestAccountVerificationCodeControllerHolder {

        private static final RequestAccountVerificationCodeController INSTANCE =
                new RequestAccountVerificationCodeController(RequestAccountVerificationCodeUseCase.getInstance());
    }
}
