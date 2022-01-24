/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetShowcaseImageById;

import java.io.InputStream;
import java.time.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetShowcaseImageByIdController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetShowcaseImageByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {

            final Result<InputStream> result = usecase.execute(req.params("id"));
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                if(error instanceof ConflictError)
                    clientError(error);
                
                return;
            }
            
            if(subjectRole.isSubscriber()) {
                publicCache();
                cache(Duration.ofMinutes(30));
            }
            sendFile(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetShowcaseImageByIdController getInstance() {
        return GetShowcaseImageByIdHolder.INSTANCE;
    }
    
    private static class GetShowcaseImageByIdHolder {

        private static final GetShowcaseImageByIdController INSTANCE =
                new GetShowcaseImageByIdController(GetShowcaseImageByIdUseCase.getInstance());
    }
}
