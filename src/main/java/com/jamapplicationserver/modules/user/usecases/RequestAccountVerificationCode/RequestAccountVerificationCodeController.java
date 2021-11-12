/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestAccountVerificationCode;

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
public class RequestAccountVerificationCodeController extends BaseController {
    
    private final IUsecase useCase;
    
    @Override
    public void executeImpl() {
        
        System.out.println("RequestUserVerificationRequestController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
        
        try {
            
            final Result result = useCase.execute(fields.get("mobile"));
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                if(error instanceof ConflictError)
                    conflict(error);
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
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
