/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserProfileImage;

import java.util.*;
import java.io.InputStream;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.domain.User;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

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

            final Result<InputStream> result = this.useCase.execute(this.req.params("id"));
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                if(error instanceof ConflictError)
                    this.conflict(error);
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                return;
            }
            
            this.sendFile(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
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
