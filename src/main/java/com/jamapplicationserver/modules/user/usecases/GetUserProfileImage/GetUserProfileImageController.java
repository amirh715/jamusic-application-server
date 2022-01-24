/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserProfileImage;

import java.io.InputStream;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class GetUserProfileImageController extends BaseController {
    
    private final IUsecase useCase;
    
    private GetUserProfileImageController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
                
        try {

            final Result<InputStream> result = useCase.execute(req.params("id"));
            
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
            
            sendFile(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetUserProfileImageController getInstance() {
        return GetUserProfileImageControllerHolder.INSTANCE;
    }
    
    private static class GetUserProfileImageControllerHolder {

        private static final GetUserProfileImageController INSTANCE =
                new GetUserProfileImageController(GetUserProfileImageUseCase.getInstance());
    }
}
