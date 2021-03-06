/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.PlayTrack;

import com.jamapplicationserver.modules.library.infra.DTOs.commands.PlayTrackRequestDTO;
import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author dada
 */
public class PlayTrackController extends BaseController {
    
    private final IUsecase usecase;
    
    private PlayTrackController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final PlayTrackRequestDTO dto =
                    new PlayTrackRequestDTO(
                            subjectId,
                            subjectRole,
                            fields.get("trackId"),
                            fields.get("playedAt")
                    );
            
            final Result result = this.usecase.execute(dto);
            
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
            
            this.noContent();
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static PlayTrackController getInstance() {
        return PlayTrackControllerHolder.INSTANCE;
    }
    
    private static class PlayTrackControllerHolder {

        private static final PlayTrackController INSTANCE =
                new PlayTrackController(PlayTrackUseCase.getInstance());
    }
}
