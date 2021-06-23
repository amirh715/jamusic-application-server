/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ChangePassword;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import java.util.*;
import com.jamapplicationserver.core.domain.IUseCase;

/**
 *
 * @author amirhossein
 */
public class ChangePasswordController extends BaseController {
    
    private final IUseCase useCase;
    
    private ChangePasswordController(IUseCase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("ChangePasswordController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        final ChangePasswordRequestDTO dto =
                new ChangePasswordRequestDTO(
                        fields.get("id"),
                        fields.get("currentPassword"),
                        fields.get("newPassword"),
                        fields.get("updaterId")
                );
        
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
    
    public static ChangePasswordController getInstance() {
        return ChangePasswordControllerHolder.INSTANCE;
    }
    
    private static class ChangePasswordControllerHolder {

        private static final ChangePasswordController INSTANCE =
                new ChangePasswordController(ChangePasswordUseCase.getInstance());
    }
}
