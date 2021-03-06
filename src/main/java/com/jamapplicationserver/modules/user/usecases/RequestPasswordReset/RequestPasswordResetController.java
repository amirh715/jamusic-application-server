/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestPasswordReset;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import java.util.*;
import com.jamapplicationserver.core.domain.IUsecase;

/**
 *
 * @author amirhossein
 */
public class RequestPasswordResetController extends BaseController {
    
    private final IUsecase useCase;
    
    private RequestPasswordResetController(IUsecase useCase) {
        this.useCase = useCase;
        this.requireAuthClaims = false;
    }
    
    @Override
    public void executeImpl() {
                
        final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        final RequestPasswordResetRequestDTO dto = new RequestPasswordResetRequestDTO(fields.get("mobile"));
        
        try {
            
            final Result result = this.useCase.execute(dto);
            
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
    
    public static RequestPasswordResetController getInstance() {
        return RequestPasswordResetControllerHolder.INSTANCE;
    }
    
    private static class RequestPasswordResetControllerHolder {

        private static final RequestPasswordResetController INSTANCE =
                new RequestPasswordResetController(RequestPasswordResetUseCase.getInstance());
    }
}
