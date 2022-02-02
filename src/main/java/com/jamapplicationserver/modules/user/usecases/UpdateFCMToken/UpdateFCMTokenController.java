/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.UpdateFCMToken;

import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author dada
 */
public class UpdateFCMTokenController extends BaseController {
    
    private final IUsecase usecase;
    
    private UpdateFCMTokenController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
            
            final UpdateFCMTokenRequestDTO dto = new UpdateFCMTokenRequestDTO(
                    fields.get("fcmToken"),
                    subjectId,
                    subjectRole
            );
            
            final Result result = usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                if(error instanceof ConflictError)
                    conflict(error);
                
                if(error instanceof NotFoundError)
                    notFound(error);
             
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
        }
        
    }
    
    public static UpdateFCMTokenController getInstance() {
        return UpdateFCMTokenUseCaseHolder.INSTANCE;
    }
    
    private static class UpdateFCMTokenUseCaseHolder {

        private static final UpdateFCMTokenController INSTANCE =
                new UpdateFCMTokenController(UpdateFCMTokenUseCase.getInstance());
    }
}
