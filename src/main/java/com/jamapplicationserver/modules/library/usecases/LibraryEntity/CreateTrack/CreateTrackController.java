/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateTrack;

import java.util.*;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.CreateTrackRequestDTO;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author dada
 */
public class CreateTrackController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreateTrackController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final HttpServletRequest raw = this.req.raw();
            final Map<String, String> fields = MultipartFormDataUtil.toMap(raw);
            
            final InputStream image = MultipartFormDataUtil.toInputStream(raw.getPart("image"));
            final InputStream audio = MultipartFormDataUtil.toInputStream(raw.getPart("audio"));
            
            final CreateTrackRequestDTO dto =
                    new CreateTrackRequestDTO(
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("genreIds"),
                            fields.get("tags"),
                            fields.get("flagNote"),
                            fields.get("lyrcis"),
                            fields.get("artistId"),
                            fields.get("recordLabel"),
                            fields.get("producer"),
                            fields.get("releaseYear"),
                            subjectId,
                            subjectRole,
                            image,
                            audio,
                            fields.get("albumId")
                    );
            
            final Result result = this.usecase.execute(dto);
            
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
            
            created();
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static CreateTrackController getInstance() {
        return CreateTrackControllerHolder.INSTANCE;
    }
    
    private static class CreateTrackControllerHolder {

        private static final CreateTrackController INSTANCE =
                new CreateTrackController(CreateTrackUseCase.getInstance());
    }
}
