/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.PublishOrArchiveLibraryEntity;

import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;
import java.util.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

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
            
            final PublishOrArchiveLibraryEntityRequestDTO dto =
                    new PublishOrArchiveLibraryEntityRequestDTO(
                            fields.get("id"),
                            fields.get("publish"),
                            fields.get("cascadePublishCommandToArtistsArtworks"),
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
            
            this.noContent();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            fail(e);
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
