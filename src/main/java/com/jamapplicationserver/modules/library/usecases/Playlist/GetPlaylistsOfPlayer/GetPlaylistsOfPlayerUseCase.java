/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlaylistsOfPlayer;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.library.infra.services.*;

/**
 *
 * @author dada
 */
public class GetPlaylistsOfPlayerUseCase
        implements IUsecase<GetPlaylistsOfPlayerRequestDTO, Set<PlaylistDetails>> {
    
    private final ILibraryQueryService queryService;
    
    private GetPlaylistsOfPlayerUseCase(ILibraryQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<Set<PlaylistDetails>> execute(GetPlaylistsOfPlayerRequestDTO request) throws GenericAppException {
        
        try {
            
            return Result.ok(queryService.getAllPlaylistsOfPlayer(request.subjectId));
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetPlaylistsOfPlayerUseCase getInstance() {
        return GetPlaylistsByPlayerIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetPlaylistsByPlayerIdUseCaseHolder {

        private static final GetPlaylistsOfPlayerUseCase INSTANCE =
                new GetPlaylistsOfPlayerUseCase(LibraryQueryService.getInstance());
    }
}
