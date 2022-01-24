/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserByID;

import java.time.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.UserDetails;

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
            
            final Result<UserDetails> result = useCase.execute(req.params(":id"));
            
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
            
            if(subjectRole.isSubscriber()) {
                privateCache();
                cache(Duration.ofMinutes(10));
            }
            
            ok(result.getValue());
            
        } catch(GenericAppException e) {
            fail(e);
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
