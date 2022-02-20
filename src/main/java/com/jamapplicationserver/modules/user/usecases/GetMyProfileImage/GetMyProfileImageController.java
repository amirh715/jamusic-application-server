/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetMyProfileImage;

import java.io.InputStream;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import java.time.Duration;

/**
 *
 * @author dada
 */
public class GetMyProfileImageController extends BaseController {
    
    private IUsecase usecase;
    
    private GetMyProfileImageController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
                        
            final Result<InputStream> result = usecase.execute(subjectId);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                return;
            }
            
            final InputStream image = result.getValue();
            
            privateCache();
            staleIfError(Duration.ofDays(3));
            cache(Duration.ofMinutes(5));
//            attachEtag(ETag.create(image));
            sendFile(image);
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetMyProfileImageController getInstance() {
        return GetMyProfileImageControllerHolder.INSTANCE;
    }
    
    private static class GetMyProfileImageControllerHolder {

        private static final GetMyProfileImageController INSTANCE =
                new GetMyProfileImageController(GetMyProfileImageUseCase.getInstance());
    }
}
