/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlaylistsOfPlayer;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;

/**
 *
 * @author dada
 */
public class GetPlaylistsOfPlayerController extends BaseController {
    
    private final IUsecase<GetPlaylistsOfPlayerRequestDTO, Set<PlaylistDetails>> usecase;
    
    private GetPlaylistsOfPlayerController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final GetPlaylistsOfPlayerRequestDTO dto =
                    new GetPlaylistsOfPlayerRequestDTO(subjectId, subjectRole);
            
            final Result<Set<PlaylistDetails>> result = usecase.execute(dto);
            
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
            
            if(subjectRole.isSubscriber()) {
                staleIfError(Duration.ofDays(4));
                staleWhileRevalidate(Duration.ofDays(4));
            }
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetPlaylistsOfPlayerController getInstance() {
        return GetPlaylistsByPlayerIdHolder.INSTANCE;
    }
    
    private static class GetPlaylistsByPlayerIdHolder {

        private static final GetPlaylistsOfPlayerController INSTANCE =
                new GetPlaylistsOfPlayerController(GetPlaylistsOfPlayerUseCase.getInstance());
    }
}
