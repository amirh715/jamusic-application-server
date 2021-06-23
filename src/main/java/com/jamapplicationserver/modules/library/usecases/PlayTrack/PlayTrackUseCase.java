/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.PlayTrack;

import com.jamapplicationserver.modules.library.infra.DTOs.usecases.PlayTrackRequestDTO;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 */
public class PlayTrackUseCase implements IUseCase<PlayTrackRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    
    private PlayTrackUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(PlayTrackRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityID> playerIdOrError = UniqueEntityID.createFromString(request.playerId);
            final Result<UniqueEntityID> trackIdOrError = UniqueEntityID.createFromString(request.trackId);
            final Result<DateTime> playedAtOrError = DateTime.create(request.playedAt);
            
            final Result[] combinedProps = {
                playerIdOrError,
                trackIdOrError,
                playedAtOrError
            };
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityID playerId = playerIdOrError.getValue();
            final UniqueEntityID trackId = trackIdOrError.getValue();
            final DateTime playedAt= playedAtOrError.getValue();
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static PlayTrackUseCase getInstance() {
        return PlayTrackUseCaseHolder.INSTANCE;
    }
    
    private static class PlayTrackUseCaseHolder {

        private static final PlayTrackUseCase INSTANCE =
                new PlayTrackUseCase(LibraryEntityRepository.getInstance());
    }
}
