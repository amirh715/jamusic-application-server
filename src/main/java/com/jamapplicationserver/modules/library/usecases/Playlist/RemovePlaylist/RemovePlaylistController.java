/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.RemovePlaylist;

import java.util.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.RemovePlaylistRequestDTO;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class RemovePlaylistController extends BaseController {
    
    private final IUsecase usecase;
    
    private RemovePlaylistController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields =
                    MultipartFormDataUtil.toMap(req.raw());
            
            final RemovePlaylistRequestDTO dto =
                    new RemovePlaylistRequestDTO(
                            fields.get("id"),
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
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static RemovePlaylistController getInstance() {
        return RemovePlaylistControllerHolder.INSTANCE;
    }
    
    private static class RemovePlaylistControllerHolder {

        private static final RemovePlaylistController INSTANCE =
                new RemovePlaylistController(RemovePlaylistUseCase.getInstance());
    }
}
