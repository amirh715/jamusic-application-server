/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.PlayTrack;

import com.jamapplicationserver.modules.library.infra.DTOs.usecases.PlayTrackRequestDTO;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class PlayTrackUseCase implements IUsecase<PlayTrackRequestDTO, String> {
    
    private final ILibraryEntityRepository libraryRepository;
    private final IPlayerRepository playerRepository;
    
    private PlayTrackUseCase(
            ILibraryEntityRepository libraryRepository,
            IPlayerRepository playerRepository
    ) {
        this.libraryRepository = libraryRepository;
        this.playerRepository = playerRepository;
    }
    
    @Override
    public Result execute(PlayTrackRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> playerIdOrError = UniqueEntityId.createFromString(request.playerId);
            final Result<UniqueEntityId> trackIdOrError = UniqueEntityId.createFromString(request.trackId);
            final Result<DateTime> playedAtOrError = DateTime.create(request.playedAt);
            
            final Result[] combinedProps = {
                playerIdOrError,
                trackIdOrError,
                playedAtOrError
            };
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId playerId = playerIdOrError.getValue();
            final UniqueEntityId trackId = trackIdOrError.getValue();
            final DateTime playedAt= playedAtOrError.getValue();
            
            final Track track =
                    this.libraryRepository.fetchTrackById(trackId)
                            .includeUnpublished()
                            .getSingleResult();
            
            if(track == null) return Result.fail(new TrackDoesNotExistError());
            
            final Player player = playerRepository.fetchById(playerId);
            
            if(player == null) return Result.fail(new PlayerDoesNotExistError());
            
            player.played(track, playedAt);
            
            playerRepository.save(player);
            
            return Result.ok();
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static PlayTrackUseCase getInstance() {
        return PlayTrackUseCaseHolder.INSTANCE;
    }
    
    private static class PlayTrackUseCaseHolder {

        private static final PlayTrackUseCase INSTANCE =
                new PlayTrackUseCase(
                        LibraryEntityRepository.getInstance(),
                        PlayerRepository.getInstance()
                );
    }
}
