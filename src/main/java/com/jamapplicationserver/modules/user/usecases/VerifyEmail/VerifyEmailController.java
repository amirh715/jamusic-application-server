/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyEmail;

import java.util.Map;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author amirhossein
 */
public class VerifyEmailController extends BaseController {
    
    private final IUseCase useCase;
    
    private VerifyEmailController(IUseCase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("VerifyEmailController");
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
            
            final VerifyEmailRequestDTO dto = new VerifyEmailRequestDTO(fields.get("link"));
            
            final Result result = this.useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ConflictError)
                    this.conflict(error);
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.noContent();
                    
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static VerifyEmailController getInstance() {
        return VerifyEmailControllerHolder.INSTANCE;
    }
    
    private static class VerifyEmailControllerHolder {

        private static final VerifyEmailController INSTANCE =
                new VerifyEmailController(VerifyEmailUseCase.getInstance());
    }
}
