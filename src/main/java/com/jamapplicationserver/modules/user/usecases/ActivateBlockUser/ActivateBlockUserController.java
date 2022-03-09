/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ActivateBlockUser;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import java.util.*;
import com.jamapplicationserver.core.domain.IUsecase;

/**
 *
 * @author amirhossein
 */
public class ActivateBlockUserController extends BaseController {
    
    private final IUsecase useCase;
    
    private ActivateBlockUserController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
                
        Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        final ActivateBlockUserRequestDTO dto =
                new ActivateBlockUserRequestDTO(
                        fields.get("id"),
                        fields.get("state"),
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
                
                if(error instanceof ForbiddenError)
                    forbidden(error);
                
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static ActivateBlockUserController getInstance() {
        return ActivateBlockUserControllerHolder.INSTANCE;
    }
    
    private static class ActivateBlockUserControllerHolder {

        private static final ActivateBlockUserController INSTANCE =
                new ActivateBlockUserController(ActivateBlockUserUseCase.getInstance());
    }
}
