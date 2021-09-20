/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RemoveUser;

import com.jamapplicationserver.modules.user.domain.errors.UserDoesNotExistError;
import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author amirhossein
 */
public class RemoveUserController extends BaseController {
    
    private final IUsecase useCase;
    
    private RemoveUserController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("RemoveUserController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        
        try {
            
            final RemoveUserRequestDTO dto =
                    new RemoveUserRequestDTO(
                            fields.get("id"),
                            fields.get("updaterId")
                    );
            
            final Result result = this.useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ConflictError)
                    this.notFound(error);
                
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                return;
            }
            
            this.noContent();
            
        } catch(Exception e) {
            this.fail(e.getMessage());
        }
        
    }
    
    public static RemoveUserController getInstance() {
        return RemoveUserControllerHolder.INSTANCE;
    }
    
    private static class RemoveUserControllerHolder {

        private static final RemoveUserController INSTANCE =
                new RemoveUserController(RemoveUserUseCase.getInstance());
    }
}
