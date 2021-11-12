/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RemoveUser;

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
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
        
        try {
            
            final RemoveUserRequestDTO dto =
                    new RemoveUserRequestDTO(
                            fields.get("id"),
                            subjectId,
                            subjectRole
                    );
            
            final Result result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ConflictError)
                    notFound(error);
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            fail(e.getMessage());
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
