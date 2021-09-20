/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.EditNotification;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class EditNotificationController extends BaseController {
    
    private final IUsecase usecase;
    
    private EditNotificationController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Result result = this.usecase.execute(null);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                if(error instanceof ConflictError)
                    this.conflict(error);
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static EditNotificationController getInstance() {
        return EditNotificationControllerHolder.INSTANCE;
    }
    
    private static class EditNotificationControllerHolder {

        private static final EditNotificationController INSTANCE =
                new EditNotificationController(EditNotificationUseCase.getInstance());
    }
}
