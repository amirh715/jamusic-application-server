/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.EditNotification;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.EditNotificationRequestDTO;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

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
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
            
            final EditNotificationRequestDTO dto =
                    new EditNotificationRequestDTO(
                            fields.get("id"),
                            fields.get("type"),
                            fields.get("title"),
                            fields.get("message"),
                            fields.get("route"),
                            fields.get("scheduledOn"),
                            fields.get("sendNow"),
                            fields.get("recipients"),
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
    
    public static EditNotificationController getInstance() {
        return EditNotificationControllerHolder.INSTANCE;
    }
    
    private static class EditNotificationControllerHolder {

        private static final EditNotificationController INSTANCE =
                new EditNotificationController(EditNotificationUseCase.getInstance());
    }
}
