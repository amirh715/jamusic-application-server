/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyEmail;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class VerifyEmailController extends BaseController {
    
    private final IUsecase useCase;
    
    private VerifyEmailController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("VerifyEmailController");
        
        try {
            
            final VerifyEmailRequestDTO dto = new VerifyEmailRequestDTO(req.queryParams("token"));
            
            final Result result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ConflictError)
                    conflict(error);
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                return;
            }
            
            noContent();
                    
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
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
