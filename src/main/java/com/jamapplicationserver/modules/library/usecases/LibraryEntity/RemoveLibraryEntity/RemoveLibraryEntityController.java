/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.RemoveLibraryEntity;

import com.jamapplicationserver.modules.library.infra.DTOs.commands.RemoveLibraryEntityRequestDTO;
import java.util.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class RemoveLibraryEntityController extends BaseController {
    
    private final IUsecase usecase;
    
    private RemoveLibraryEntityController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
            
            final String id = fields.get("id");
            final RemoveLibraryEntityRequestDTO dto =
                    new RemoveLibraryEntityRequestDTO(
                            id,
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
    
    public static RemoveLibraryEntityController getInstance() {
        return RemoveLibraryEntityControllerHolder.INSTANCE;
    }
    
    private static class RemoveLibraryEntityControllerHolder {

        private static final RemoveLibraryEntityController INSTANCE =
                new RemoveLibraryEntityController(RemoveLibraryEntityUseCase.getInstance());
    }
}
