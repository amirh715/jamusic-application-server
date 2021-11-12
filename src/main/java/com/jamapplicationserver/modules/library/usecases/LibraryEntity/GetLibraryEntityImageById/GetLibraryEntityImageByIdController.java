/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityImageById;

import java.util.*;
import java.time.*;
import java.io.InputStream;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntityImageByIdController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetLibraryEntityImageByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = req.params();
            
            final Result<InputStream> result = usecase.execute(fields.get(":id"));
            
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
            
            cache(Duration.ofMinutes(5));
//            final ETag etag = ETag.create(result.getValue());
//            if(etag.same(getEtag())) {
//                notModified();
//                return;
//            }
            
//            attachEtag(etag);
            sendFile(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetLibraryEntityImageByIdController getInstance() {
        return GetLibraryEntityImageByIdHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityImageByIdHolder {

        private static final GetLibraryEntityImageByIdController INSTANCE =
                new GetLibraryEntityImageByIdController(GetLibraryEntityImageByIdUseCase.getInstance());
    }
}
