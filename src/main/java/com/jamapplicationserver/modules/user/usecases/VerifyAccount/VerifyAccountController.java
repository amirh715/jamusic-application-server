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
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class VerifyAccountController extends BaseController {
    
    private final IUsecase useCase;
    
    @Override
    public void executeImpl() {
                
        final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
        
        try {
                        
            final VerifyAccountRequestDTO dto = new VerifyAccountRequestDTO(
                    fields.get("mobileNo"),
                    fields.get("code")
            );
            
            final Result result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                if(error instanceof ConflictError)
                    clientError(error);
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            fail(e);
        }
        
    }
    
    private VerifyAccountController(IUsecase useCase) {
        this.useCase = useCase;
        this.requireAuthClaims = false;
    }
    
    public static VerifyAccountController getInstance() {
        return VerifyAccountControllerHolder.INSTANCE;
    }
    
    private static class VerifyAccountControllerHolder {

        private static final VerifyAccountController INSTANCE =
                new VerifyAccountController(VerifyAccountUseCase.getInstance());
    }
}
