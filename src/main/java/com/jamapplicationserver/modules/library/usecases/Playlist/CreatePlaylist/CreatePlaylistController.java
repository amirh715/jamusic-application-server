/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.CreatePlaylist;

import java.util.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.CreatePlaylistRequestDTO;

/**
 *
 * @author dada
 */
public class CreatePlaylistController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreatePlaylistController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields =
                    MultipartFormDataUtil.toMap(req.raw());
            
            final CreatePlaylistRequestDTO dto =
                    new CreatePlaylistRequestDTO(
                            fields.get("title"),
                            fields.get("trackIds"),
                            subjectId,
                            subjectRole
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
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static CreatePlaylistController getInstance() {
        return CreatePlaylistControllerHolder.INSTANCE;
    }
    
    private static class CreatePlaylistControllerHolder {

        private static final CreatePlaylistController INSTANCE =
                new CreatePlaylistController(CreatePlaylistUseCase.getInstance());
    }
}
