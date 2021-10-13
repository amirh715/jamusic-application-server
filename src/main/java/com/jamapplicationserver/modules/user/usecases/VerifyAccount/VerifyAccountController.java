/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyAccount;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author amirhossein
 */
public class VerifyAccountController extends BaseController {
    
    private final IUsecase useCase;
    
    @Override
    public void executeImpl() {
        
        System.out.println("VerifyAccountController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        
        try {
            
            final VerifyAccountRequestDTO dto = new VerifyAccountRequestDTO(
                    fields.get("id"),
                    fields.get("resetCode")
            );
            
            final Result result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                if(error instanceof ConflictError)
                    this.clientError(error);
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                return;
            }
            
            this.noContent();
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    private VerifyAccountController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    public static VerifyAccountController getInstance() {
        return VerifyAccountControllerHolder.INSTANCE;
    }
    
    private static class VerifyAccountControllerHolder {

        private static final VerifyAccountController INSTANCE =
                new VerifyAccountController(VerifyAccountUseCase.getInstance());
    }
}
