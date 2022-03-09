/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.CreatePlaylist;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.CreatePlaylistRequestDTO;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.modules.library.domain.Player.Player;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class CreatePlaylistUseCase implements IUsecase<CreatePlaylistRequestDTO, String> {
    
    private final IPlayerRepository playerRepository;
    private final ILibraryEntityRepository libraryRepository;
    
    private CreatePlaylistUseCase(
            IPlayerRepository playerRepository,
            ILibraryEntityRepository libraryRepository
    ) {
        this.playerRepository = playerRepository;
        this.libraryRepository = libraryRepository;
    }
    
    @Override
    public Result execute(CreatePlaylistRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<Title> titleOrError = Title.create(request.title);
            
            combinedProps.add(titleOrError);
            
            final Set<UniqueEntityId> trackIds = new HashSet<>();
            if(!request.trackIds.isEmpty()) {
                final Result<Set<UniqueEntityId>> trackIdsOrError =
                        UniqueEntityId.createFromStrings(request.trackIds);
                if(trackIdsOrError.isFailure) return trackIdsOrError;
                trackIds.addAll(trackIdsOrError.getValue());
            }
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final Title title = titleOrError.getValue();
            
            final Player player = playerRepository.fetchById(request.subjectId);
            if(player == null) return Result.fail(new PlayerDoesNotExistError());
            
            final Set<Track> tracks = new HashSet<>();

            for(UniqueEntityId trackId : trackIds) {
                final Track track =
                        libraryRepository.fetchTrackById(trackId).getSingleResult();
                if(track == null) return Result.fail(new TrackDoesNotExistError());
                tracks.add(track);
            }
            
            
            final Result<Playlist> playlistOrError =
                    Playlist.create(title, tracks);
            if(playlistOrError.isFailure) return playlistOrError;
            
            final Playlist playlist = playlistOrError.getValue();
            
            final Result result = player.addPlaylist(playlist);
            if(result.isFailure) return result;

            playerRepository.save(player);
            
            return Result.ok();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
        
    }
    
    public static CreatePlaylistUseCase getInstance() {
        return CreatePlaylistUseCaseHolder.INSTANCE;
    }
    
    private static class CreatePlaylistUseCaseHolder {

        private static final CreatePlaylistUseCase INSTANCE =
                new CreatePlaylistUseCase(
                        PlayerRepository.getInstance(),
                        LibraryEntityRepository.getInstance()
                );
    }
}
