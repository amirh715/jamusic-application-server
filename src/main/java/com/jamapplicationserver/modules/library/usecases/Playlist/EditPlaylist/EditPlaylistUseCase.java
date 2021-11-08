/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.EditPlaylist;

import java.util.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Playlist.*;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.domain.Track.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.EditPlaylistRequestDTO;

/**
 *
 * @author dada
 */
public class EditPlaylistUseCase implements IUsecase<EditPlaylistRequestDTO, String> {
    
    private final ILibraryEntityRepository libraryRepository;
    private final IPlayerRepository playerRepository;
    
    private EditPlaylistUseCase(
            ILibraryEntityRepository libraryRepository,
            IPlayerRepository playerRepository
    ) {
        this.libraryRepository = libraryRepository;
        this.playerRepository = playerRepository;
    }
    
    @Override
    public Result execute(EditPlaylistRequestDTO request) throws GenericAppException {
        
        try {
            
            final Set<Result> combinedProps = new HashSet<>();
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            final Result<Title> titleOrError = Title.create(request.title);
            
            final Set<UniqueEntityId> tracksIds = new HashSet<>();
            if(request.trackIds != null && !request.trackIds.isEmpty()) {
                
                final Result<Set<UniqueEntityId>> trackIdsOrError =
                        UniqueEntityId.createFromStrings(request.trackIds);
                if(trackIdsOrError.isFailure)
                    return trackIdsOrError;
                tracksIds.addAll(trackIdsOrError.getValue());
                
            }
            
            combinedProps.add(idOrError);
            
            if(titleOrError.isFailure)
                combinedProps.add(titleOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId playlistId = idOrError.getValue();
            final UniqueEntityId playerId = request.subjectId;
            final Title title = request.title != null ? titleOrError.getValue() : null;
            
            final Set<Track> tracks = new HashSet<>();
            for(UniqueEntityId trackId : tracksIds) {
                final Track track = libraryRepository.fetchTrackById(trackId).getSingleResult();
                if(track == null) return Result.fail(new TrackDoesNotExistError());
                tracks.add(track);
            }
            
            final Player player = playerRepository.fetchById(playerId);
            if(player == null) return Result.fail(new PlayerDoesNotExistError());
            
            final Playlist playlist = player.getPlaylist(playlistId);
            if(playlist == null) return Result.fail(new PlaylistDoesNotExistError());
            
            final Result result = playlist.change(title, tracks);
            if(result.isFailure) return result;
            
            playerRepository.save(player);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static EditPlaylistUseCase getInstance() {
        return EditPlaylistControlleHolder.INSTANCE;
    }
    
    private static class EditPlaylistControlleHolder {

        private static final EditPlaylistUseCase INSTANCE =
                new EditPlaylistUseCase(
                        LibraryEntityRepository.getInstance(),
                        PlayerRepository.getInstance()
                );
    }
}
