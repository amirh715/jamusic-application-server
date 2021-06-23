/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.EditLibraryEntity;

import com.jamapplicationserver.modules.library.infra.DTOs.usecases.EditLibraryEntityRequestDTO;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author dada
 */
public class EditLibraryEntityController extends BaseController {
    
    private final IUseCase usecase;
    
    private EditLibraryEntityController(IUseCase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final HttpServletRequest raw = this.req.raw();
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(raw);
            final InputStream image = MultipartFormDataUtil.toInputStream(raw.getPart("image"));
            
            final EditLibraryEntityRequestDTO dto =
                    new EditLibraryEntityRequestDTO(
                            fields.get("type"),
                            fields.get("id"),
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("tags"),
                            fields.get("genres"),
                            fields.get("flagNote"),
                            fields.get("instagramId"),
                            image
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
            this.fail(e);
        }
        
    }
    
    public static EditLibraryEntityController getInstance() {
        return EditLibraryEntityControllerHolder.INSTANCE;
    }
    
    private static class EditLibraryEntityControllerHolder {

        private static final EditLibraryEntityController INSTANCE =
                new EditLibraryEntityController(EditLibraryEntityUseCase.getInstance());
    }
}
