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

/**
 *
 * @author dada
 */
public class CreatePlaylistUseCase implements IUsecase<CreatePlaylistRequestDTO, String> {
    
    private final IPlaylistRepository playlistRepository;
    private final IPlayerRepository playerRepository;
    private final ILibraryEntityRepository libraryRepository;
    
    private CreatePlaylistUseCase(
            IPlaylistRepository playlistRepository,
            IPlayerRepository playerRepository,
            ILibraryEntityRepository libraryRepository
    ) {
        this.playlistRepository = playlistRepository;
        this.playerRepository = playerRepository;
        this.libraryRepository = libraryRepository;
    }
    
    @Override
    public Result execute(CreatePlaylistRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<UniqueEntityId> playerIdOrError = UniqueEntityId.createFromString(request.playerId);
            
            combinedProps.add(titleOrError);
            combinedProps.add(playerIdOrError);
            
            request.trackIds
                    .forEach(trackId -> combinedProps.add(UniqueEntityId.createFromString(trackId)));
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final Title title = titleOrError.getValue();
            final UniqueEntityId playerId = playerIdOrError.getValue();
            final Set<UniqueEntityId> trackIds = null;
            
            final Player player = this.playerRepository.fetchById(playerId);
            if(player == null) return Result.fail(new PlayerDoesNotExistError());
            
            final Set<Track> tracks = new HashSet<>();
            for(UniqueEntityId trackId : trackIds) {
                final Track track =
                        this.libraryRepository.fetchTrackById(trackId).getSingleResult();
                if(track == null) return Result.fail(new TrackDoesNotExistError());
                tracks.add(track);
            }
            
            final Result<Playlist> playlistOrError =
                    Playlist.create(title, tracks);
            if(playlistOrError.isFailure) return playlistOrError;
            
            final Playlist playlist = playlistOrError.getValue();
            
            final Result result = player.addPlaylist(playlist);
            if(result.isFailure) return result;
            
            // save player too ??
            this.playerRepository.save(player);
            
            this.playlistRepository.save(playlist);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static CreatePlaylistUseCase getInstance() {
        return CreatePlaylistUseCaseHolder.INSTANCE;
    }
    
    private static class CreatePlaylistUseCaseHolder {

        private static final CreatePlaylistUseCase INSTANCE =
                new CreatePlaylistUseCase(
                        PlaylistRepository.getInstance(),
                        PlayerRepository.getInstance(),
                        LibraryEntityRepository.getInstance()
                );
    }
}
