/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ActivateBlockUser;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import java.util.*;
import com.jamapplicationserver.core.domain.IUseCase;

/**
 *
 * @author amirhossein
 */
public class ActivateBlockUserController extends BaseController {
    
    private final IUseCase useCase;
    
    private ActivateBlockUserController(IUseCase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("ActivateBlockUserController");
        
        Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        final ActivateBlockUserRequestDTO dto =
                new ActivateBlockUserRequestDTO(
                        fields.get("id"),
                        fields.get("state"),
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
                
                if(error instanceof ForbiddenError)
                    this.forbidden(error);
                
                return;
            }
            
            this.noContent();
            
        } catch(Exception e) {
            this.fail(e);
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
