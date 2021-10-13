/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.EditArtwork;

import java.util.*;
import java.io.InputStream;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.EditArtworkRequestDTO;

/**
 *
 * @author dada
 */
public class EditArtworkController extends BaseController {
    
    private final IUsecase usecase;
    
    private EditArtworkController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            final InputStream image =
                    MultipartFormDataUtil.toInputStream(this.req.raw().getPart("image"));
            
            final EditArtworkRequestDTO dto =
                    new EditArtworkRequestDTO(
                            fields.get("id"),
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("genreIds"),
                            fields.get("tags"),
                            fields.get("flagNote"),
                            fields.get("recordLabel"),
                            fields.get("producer"),
                            fields.get("releaseYear"),
                            fields.get("lyrics"),
                            image,
                            fields.get("removeImage"),
                            subjectId,
                            subjectRole
                    );
            
            final Result result = usecase.execute(dto);
            
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
    
    public static EditArtworkController getInstance() {
        return EditArtworkControllerHolder.INSTANCE;
    }
    
    private static class EditArtworkControllerHolder {

        private static final EditArtworkController INSTANCE =
                new EditArtworkController(EditArtworkUseCase.getInstance());
    }
}
