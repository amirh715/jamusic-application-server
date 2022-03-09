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
import com.jamapplicationserver.core.domain.IUsecase;

/**
 *
 * @author amirhossein
 */
public class ChangePasswordController extends BaseController {
    
    private final IUsecase useCase;
    
    private ChangePasswordController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
                
        final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
        final ChangePasswordRequestDTO dto =
                new ChangePasswordRequestDTO(
                        fields.get("id"),
                        fields.get("currentPassword"),
                        fields.get("newPassword"),
                        subjectId,
                        subjectRole
                );
        
        try {
            
            final Result result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                if(error instanceof ConflictError)
                    conflict(error);
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                    
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
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
