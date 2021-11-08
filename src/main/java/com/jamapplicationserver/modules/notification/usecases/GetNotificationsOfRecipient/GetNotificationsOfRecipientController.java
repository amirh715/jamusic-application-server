/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationsOfRecipient;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.*;

/**
 *
 * @author dada
 */
public class GetNotificationsOfRecipientController extends BaseController {
    
    private IUsecase<GetNotificationsOfRecipientRequestDTO, RecipientDetails> usecase;
    
    private GetNotificationsOfRecipientController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final GetNotificationsOfRecipientRequestDTO dto =
                    new GetNotificationsOfRecipientRequestDTO(
                            req.params(":recipientId"),
                            subjectId,
                            subjectRole
                    );
            
            final Result<RecipientDetails> result = usecase.execute(dto);
            
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
    
    public static GetNotificationsOfRecipientController getInstance() {
        return GetNotificationsOfRecipientControllerHolder.INSTANCE;
    }
    
    private static class GetNotificationsOfRecipientControllerHolder {

        private static final GetNotificationsOfRecipientController INSTANCE =
                new GetNotificationsOfRecipientController(GetNotificationsOfRecipientUseCase.getInstance());
    }
}
