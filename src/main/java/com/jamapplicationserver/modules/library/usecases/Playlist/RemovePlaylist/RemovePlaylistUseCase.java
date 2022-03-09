/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.RemovePlaylist;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;

/**
 *
 * @author dada
 */
public class RemovePlaylistUseCase implements IUsecase<RemovePlaylistRequestDTO, String> {
    
    private final IPlayerRepository repository;
    
    private RemovePlaylistUseCase(IPlayerRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(RemovePlaylistRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Player player = repository.fetchById(request.subjectId);
            if(player == null) return Result.fail(new PlayerDoesNotExistError());
            
            final Playlist playlist = player.getPlaylist(id);
            if(playlist == null) return Result.fail(new PlaylistDoesNotExistError());
                        
            player.removePlaylist(playlist);
                        
            repository.save(player);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static RemovePlaylistUseCase getInstance() {
        return RemovePlaylistUseCaseHolder.INSTANCE;
    }
    
    private static class RemovePlaylistUseCaseHolder {

        private static final RemovePlaylistUseCase INSTANCE =
                new RemovePlaylistUseCase(PlayerRepository.getInstance());
    }
}
