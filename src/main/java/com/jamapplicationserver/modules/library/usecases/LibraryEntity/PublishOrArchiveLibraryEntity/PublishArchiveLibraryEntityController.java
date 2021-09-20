/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.PublishOrArchiveLibraryEntity;

import com.jamapplicationserver.modules.library.infra.DTOs.usecases.PublishArchiveLibraryEntityRequestDTO;
import java.util.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class PublishArchiveLibraryEntityController extends BaseController {
    
    private final IUsecase usecase;
    
    private PublishArchiveLibraryEntityController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final PublishArchiveLibraryEntityRequestDTO dto =
                    new PublishArchiveLibraryEntityRequestDTO(
                            fields.get("id"),
                            fields.get("published"),
                            fields.get("cascadeToAll")
                    );
            
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
            
            this.noContent();
            
        } catch(Exception e) {
            e.printStackTrace();
            this.fail(e);
        }
        
    }
    
    public static PublishArchiveLibraryEntityController getInstance() {
        return PublishArchiveLibraryEntityControllerHolder.INSTANCE;
    }
    
    private static class PublishArchiveLibraryEntityControllerHolder {

        private static final PublishArchiveLibraryEntityController INSTANCE =
                new PublishArchiveLibraryEntityController(PublishArchiveLibraryEntityUseCase.getInstance());
    }
}
