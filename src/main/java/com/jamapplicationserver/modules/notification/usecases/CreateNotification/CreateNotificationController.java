/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.CreateNotification;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.CreateNotificationRequestDTO;

/**
 *
 * @author dada
 */
public class CreateNotificationController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreateNotificationController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final CreateNotificationRequestDTO dto =
                    new CreateNotificationRequestDTO(
                            fields.get("type"),
                            fields.get("title"),
                            fields.get("message"),
                            fields.get("route"),
                            fields.get("senderType"),
                            fields.get("scheduledOn"),
                            fields.get("recipients"),
                            MultipartFormDataUtil.toInputStream(req.raw().getPart("image")),
                            subjectId,
                            subjectRole
                    );
            
            System.out.println(fields.get("type"));
            
            final Result result = this.usecase.execute(dto);
            
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
            
            created();
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
        }
        
    }
    
    public static CreateNotificationController getInstance() {
        return CreateNotificationControllerHolder.INSTANCE;
    }
    
    private static class CreateNotificationControllerHolder {

        private static final CreateNotificationController INSTANCE =
                new CreateNotificationController(CreateNotificationUseCase.getInstance());
    }
}
