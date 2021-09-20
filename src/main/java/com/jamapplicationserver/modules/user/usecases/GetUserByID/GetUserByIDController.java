/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserByID;

import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.user.domain.*;

/**
 *
 * @author amirhossein
 */
public class GetUserByIDController extends BaseController {
    
    private final IUsecase useCase;
    
    private GetUserByIDController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("GetUserByIDController");
        
        try {
            
            final Result<User> result = this.useCase.execute(this.req.params(":id"));
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ValidationError)
                    this.clientError(error);
                
                if(error instanceof UserDoesNotExistError)
                    this.notFound(error);
                
                return;            
            }
            
            this.ok(result.getValue());
            
        } catch(GenericAppException e) {
            this.fail(e);
        }

    }
    
    public static GetUserByIDController getInstance() {
        return GetUserByIDControllerHolder.INSTANCE;
    }
    
    private static class GetUserByIDControllerHolder {

        private static final GetUserByIDController INSTANCE =
                new GetUserByIDController(GetUserByIDUseCase.getInstance());
    }
}
