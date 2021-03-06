/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.PlayTrack;

import com.jamapplicationserver.modules.library.infra.DTOs.commands.PlayTrackRequestDTO;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

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
            
            final Result<UniqueEntityId> trackIdOrError = UniqueEntityId.createFromString(request.trackId);
            final Result<DateTime> playedAtOrError = DateTime.create(request.playedAt);
            
            final Result[] combinedProps = {
                trackIdOrError,
                playedAtOrError
            };
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId trackId = trackIdOrError.getValue();
            final DateTime playedAt= playedAtOrError.getValue();
            
            final Track track =
                    libraryRepository.fetchTrackById(trackId)
                            .includeUnpublished(request.subjectRole)
                            .getSingleResult();
            
            if(track == null) return Result.fail(new TrackDoesNotExistError());
            
            final Player player = playerRepository.fetchById(request.subjectId);
            
            if(player == null) return Result.fail(new PlayerDoesNotExistError());
            
            player.played(track, playedAt);
            
            playerRepository.save(player);
            
            return Result.ok();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
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
