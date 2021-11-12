/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.CreateUser;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.user.domain.User;

/**
 *
 * @author amirhossein
 */
public class CreateUserController extends BaseController {
    
    private final IUsecase useCase;
    
    @Override
    public void executeImpl() {
        
        System.out.println("CreateUserController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
        final CreateUserRequestDTO dto =
                new CreateUserRequestDTO(
                        fields.get("name"),
                        fields.get("mobile"),
                        fields.get("password"),
                        fields.get("email"),
                        fields.get("role"),
                        fields.get("FCM"),
                        fields.get("sendVerificationCode"),
                        subjectId,
                        subjectRole
                );
        
        try {
            
            final Result<User> result = useCase.execute(dto);

            if(result.isFailure) {

                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                if(error instanceof ConflictError)
                    conflict(error);
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                if(error instanceof AuthError)
                    unauthorized(error);
                
                return;
            }

            created();
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
        }

    }
    
    private CreateUserController(CreateUserUseCase useCase) {
        this.useCase = useCase;
        this.requireAuthClaims = false;
    }
    
    public static CreateUserController getInstance() {
        return CreateUserControllerHolder.INSTANCE;
    }
    
    private static class CreateUserControllerHolder {
        
        private static final CreateUserController INSTANCE =
                new CreateUserController(CreateUserUseCase.getInstance());
    }
    
}
