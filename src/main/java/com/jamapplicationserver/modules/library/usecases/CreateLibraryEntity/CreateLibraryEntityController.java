/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.CreateLibraryEntity;

import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateLibraryEntityRequestDTO;
import java.util.*;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.LibraryEntity;

/**
 *
 * @author dada
 */
public class CreateLibraryEntityController extends BaseController {
    
    private final IUseCase usecase;
    
    private CreateLibraryEntityController(IUseCase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final HttpServletRequest raw = this.req.raw();
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(raw);
            
            final InputStream imageFile = MultipartFormDataUtil.toInputStream(raw.getPart("image"));
            final InputStream audioFile = MultipartFormDataUtil.toInputStream(raw.getPart("audio"));
            
            final CreateLibraryEntityRequestDTO dto =
                    new CreateLibraryEntityRequestDTO(
                            fields.get("type"),
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("genres"),
                            fields.get("tags"),
                            fields.get("flagNote"),
                            fields.get("artistId"),
                            fields.get("instagramId"),
                            fields.get("lyrics"),
                            fields.get("tracks"),
                            imageFile,
                            audioFile
                    );
            
            final Result<LibraryEntity> result = this.usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                if(error instanceof ConflictError)
                    this.clientError(error);
                
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.created(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static CreateLibraryEntityController getInstance() {
        return CreateLibraryEntityControllerHolder.INSTANCE;
    }
    
    private static class CreateLibraryEntityControllerHolder {

        private static final CreateLibraryEntityController INSTANCE =
                new CreateLibraryEntityController(CreateLibraryEntityUseCase.getInstance());
    }
}
