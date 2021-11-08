/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.RemoveNotification;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.*;

/**
 *
 * @author dada
 */
public class RemoveNotificationController extends BaseController {
    
    private final IUsecase usecase;
    
    private RemoveNotificationController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
            
            final RemoveNotificationRequestDTO dto =
                    new RemoveNotificationRequestDTO(
                            fields.get("id"),
                            subjectId,
                            subjectRole
                    );
            
            final Result result = usecase.execute(dto);
            
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
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static RemoveNotificationController getInstance() {
        return RemoveNotificationControllerHolder.INSTANCE;
    }
    
    private static class RemoveNotificationControllerHolder {

        private static final RemoveNotificationController INSTANCE =
                new RemoveNotificationController(RemoveNotificationUseCase.getInstance());
    }
}
