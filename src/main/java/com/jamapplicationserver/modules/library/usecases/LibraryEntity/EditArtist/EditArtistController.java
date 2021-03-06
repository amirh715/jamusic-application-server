/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.EditArtist;

import java.util.*;
import java.io.InputStream;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.EditArtistRequestDTO;

/**
 *
 * @author dada
 */
public class EditArtistController extends BaseController {
    
    private final IUsecase usecase;
    
    private EditArtistController(
            IUsecase usecase
    ) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields =
                    MultipartFormDataUtil.toMap(req.raw());
            
            final InputStream image =
                    MultipartFormDataUtil.toInputStream(req.raw().getPart("image"));
            
            final EditArtistRequestDTO dto =
                    new EditArtistRequestDTO(
                            fields.get("id"),
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("tags"),
                            fields.get("genreIds"),
                            fields.get("flagNote"),
                            fields.get("instagramId"),
                            image,
                            fields.get("removeImage"),
                            fields.get("bandMemberIds"),
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
    
    public static EditArtistController getInstance() {
        return EditArtistControllerHolder.INSTANCE;
    }
    
    private static class EditArtistControllerHolder {

        private static final EditArtistController INSTANCE =
                new EditArtistController(EditArtistUseCase.getInstance());
    }
}
