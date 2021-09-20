/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateArtist;

import java.util.*;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.Artist;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateArtistRequestDTO;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;

/**
 *
 * @author dada
 */
public class CreateArtistController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreateArtistController(IUsecase usecase) {
        this.usecase = usecase;
    }

    @Override
    public void executeImpl() {
        
        try {
                        
            final HttpServletRequest raw = this.req.raw();
            final Map<String, String> fields = MultipartFormDataUtil.toMap(raw);
            
            final InputStream image = MultipartFormDataUtil.toInputStream(raw.getPart("image"));
                        
            final CreateArtistRequestDTO dto =
                    new CreateArtistRequestDTO(
                            fields.get("type"),
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("genreIds"),
                            fields.get("tags"),
                            fields.get("flagNote"),
                            this.subjectId,
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
            
            final Artist artist = (Artist) result.getValue();
            
            this.ok(artist);
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static CreateArtistController getInstance() {
        return CreateArtistControllerHolder.INSTANCE;
    }
    
    private static class CreateArtistControllerHolder {

        private static final CreateArtistController INSTANCE =
                new CreateArtistController(CreateArtistUseCase.getInstance());
    }
}
