/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Playlist;

import java.util.*;
import java.util.stream.*;
import java.time.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author amirhossein
 */
public class Playlist extends Entity {
    
    private static final int MAX_ALLOWED_TRACKS_PER_PLAYLIST = 150;
    
    private Title title;
    private Set<UniqueEntityId> tracksIds = new HashSet<>();
    
    // creation constructor
    private Playlist(
            Title title
    ) {
        super();
        this.title = title;
    }
    
    // reconstitution constructor
    private Playlist(
            UniqueEntityId id,
            Title title,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        super(id, createdAt, lastModifiedAt);
        this.title = title;
        this.tracksIds = new HashSet<>();
    }
    
    public final Result addTrack(UniqueEntityId trackId) {
        
        if(tracksIds.size() + 1 > MAX_ALLOWED_TRACKS_PER_PLAYLIST)
            return Result.fail(new MaxAllowedTracksPerPlaylistExceededError());
        
        tracksIds.add(trackId);
        
        modified();
        
        return Result.ok();
    }
    
    public final void removeTrack(UniqueEntityId trackId) {
        
        if(tracksIds.isEmpty()) return;
        
        if(!tracksIds.contains(trackId)) return;
        
        modified();
        
        tracksIds.remove(trackId);

    }
    
    public Result change(Title title, Set<Track> tracks) {
        
        this.title = title;
        
        if(tracks.size() + 1 > MAX_ALLOWED_TRACKS_PER_PLAYLIST)
            return Result.fail(new MaxAllowedTracksPerPlaylistExceededError());
        
        this.tracksIds =
                tracks
                .stream()
                .map(track -> track.getId())
                .collect(Collectors.toSet());
            
        modified();
        return Result.ok();
    }
    
    public Title getTitle() {
        return this.title;
    }
    
    public Set<UniqueEntityId> getTracksIds() {
        return this.tracksIds;
    }
    
    public static Result create(
            Title title,
            Set<Track> tracks
    ) {
        
        Playlist instance = new Playlist(title);
        
        if(tracks.size() + 1 > MAX_ALLOWED_TRACKS_PER_PLAYLIST)
            return Result.fail(new MaxAllowedTracksPerPlaylistExceededError());
        tracks.forEach(track -> instance.tracksIds.add(track.getId()));
        
        return Result.ok(instance);
    }
    
    public static Result<Playlist> reconstitute(
            UUID id,
            String title,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            Set<UUID> tracksIds
    ) {

        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<Set<UniqueEntityId>> tracksIdsOrError = UniqueEntityId.createFromUUIDs(tracksIds);

        Playlist instance = new Playlist(
                idOrError.getValue(),
                titleOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue()
        );

        if(tracksIds.size() > MAX_ALLOWED_TRACKS_PER_PLAYLIST)
            return Result.fail(new MaxAllowedTracksPerPlaylistExceededError());
        if(!tracksIds.isEmpty())
            instance.tracksIds.addAll(tracksIdsOrError.getValue());
        
        return Result.ok(instance);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
}
