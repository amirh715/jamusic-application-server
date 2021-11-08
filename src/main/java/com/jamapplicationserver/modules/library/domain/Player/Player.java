/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Player;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.domain.core.events.*;

/**
 *
 * @author amirhossein
 */
public class Player extends AggregateRoot {
    
    private final int MAX_ALLOWED_PLAYLISTS = 100;
    
    private final String name;
    private final Set<PlayedTrack> playedTracks;
    private final PlayerRole role;
    private final Set<Playlist> playlists;
    
    // reconstitution constructor
    private Player(
            UniqueEntityId playerId,
            String name,
            Set<PlayedTrack> playedTracks,
            Set<Playlist> playlists,
            PlayerRole role,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        super(playerId, createdAt, lastModifiedAt);
        this.name = name;
        this.playedTracks = playedTracks != null ? playedTracks : new HashSet<>();
        this.role = role;
        this.playlists = playlists != null ? playlists : new HashSet<>();
    }
    
    public static Result<Player> reconstitute(
            UUID id,
            String name,
            Set<PlayedTrack> playedTracks,
            Set<Playlist> playlists,
            String role,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt
    ) {
        
        return Result.ok(
                new Player(
                        UniqueEntityId.createFromUUID(id).getValue(),
                        name,
                        playedTracks,
                        playlists,
                        PlayerRole.create(role).getValue(),
                        DateTime.create(createdAt).getValue(),
                        DateTime.create(lastModifiedAt).getValue()
                )
        );
    }
    
    public Result addPlaylist(Playlist playlist) {
        
        if(playlists.size() >= MAX_ALLOWED_PLAYLISTS)
            return Result.fail(new MaxAllowedPlaylistsExceededError());
        
        playlists.add(playlist);
        
        addDomainEvent(new PlaylistCreated(playlist, this));
        
        return Result.ok();
    }
    
    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }
    
    public Set<Playlist> getPlaylists() {
        return this.playlists;
    }
    
    public Playlist getPlaylist(UniqueEntityId playlistId) {
        Playlist p = null;
        for(Playlist playlist : playlists) {
            if(playlist.getId().equals(playlistId))
                p = playlist;
        }
        return p;
    }
    
    public Set<PlayedTrack> getPlayedTracks() {
        return this.playedTracks;
    }
    
    public PlayerRole getPlayerRole() {
        return this.role;
    }
    
    public String getPlayerName() {
        return this.name;
    }
    
    public void played(Track track, DateTime playedAt) {
        
        final PlayedTrack playedTrack = new PlayedTrack(track.getId(), playedAt);
        
        playedTracks.add(playedTrack);

    }
    
}
