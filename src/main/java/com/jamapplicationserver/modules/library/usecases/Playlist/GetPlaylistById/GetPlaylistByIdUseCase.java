/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlaylistById;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.library.infra.services.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class GetPlaylistByIdUseCase
        implements IUsecase<GetPlaylistByIdRequestDTO, PlaylistDetails> {
    
    private ILibraryQueryService queryService;
    
    private GetPlaylistByIdUseCase(ILibraryQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<PlaylistDetails> execute(GetPlaylistByIdRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError =
                    UniqueEntityId.createFromString(request.playlistId);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId playlistId = idOrError.getValue();
            
            final PlaylistDetails playlist = queryService.getPlaylistById(playlistId, request.subjectId);
            if(playlist == null) return Result.fail(new PlaylistDoesNotExistError());
            
            return Result.ok(playlist);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetPlaylistByIdUseCase getInstance() {
        return GetPlaylistByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetPlaylistByIdUseCaseHolder {

        private static final GetPlaylistByIdUseCase INSTANCE =
                new GetPlaylistByIdUseCase(LibraryQueryService.getInstance());
    }
}
