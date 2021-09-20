/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityImageById;

import java.util.*;
import java.io.InputStream;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

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
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final Result<InputStream> result = this.usecase.execute(fields.get("id"));
            
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
            
            this.sendFile(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
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
