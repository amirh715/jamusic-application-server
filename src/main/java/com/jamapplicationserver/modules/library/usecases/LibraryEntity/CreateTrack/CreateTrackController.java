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
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateTrackRequestDTO;
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
            
            System.out.println("Audio" + raw.getPart("audio"));
            
            final InputStream image = MultipartFormDataUtil.toInputStream(raw.getPart("image"));
            final InputStream audio = MultipartFormDataUtil.toInputStream(raw.getPart("audio"));
            
            System.out.println(audio);
            
            final CreateTrackRequestDTO dto =
                    new CreateTrackRequestDTO(
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("genreIds"),
                            fields.get("tags"),
                            fields.get("flagNote"),
                            this.subjectId,
                            fields.get("lyrcis"),
                            fields.get("artistId"),
                            fields.get("recordLabel"),
                            fields.get("producer"),
                            fields.get("releaseYear"),
                            image,
                            audio,
                            fields.get("albumId")
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
            
            final Track track = (Track) result.getValue();
            
            this.ok(track);
            
        } catch(Exception e) {
            
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
