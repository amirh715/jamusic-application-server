/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationById;

import java.util.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IUsecase;

/**
 *
 * @author dada
 */
public class GetNotificationByIdController extends BaseController {
    
    private final IUsecase<String, NotificationDetails> usecase;
    
    private GetNotificationByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Result<NotificationDetails> result = usecase.execute(req.params(":id"));
            
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
            
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetNotificationByIdController getInstance() {
        return GetNotificationByIdControllerHolder.INSTANCE;
    }
    
    private static class GetNotificationByIdControllerHolder {

        private static final GetNotificationByIdController INSTANCE =
                new GetNotificationByIdController(GetNotificationByIdUseCase.getInstance());
    }
}
