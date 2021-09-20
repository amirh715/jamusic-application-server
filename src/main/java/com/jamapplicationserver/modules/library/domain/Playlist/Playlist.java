/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Playlist;

import java.util.*;
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
public class Playlist extends AggregateRoot {
    
    private static final int MAX_ALLOWED_TRACKS_PER_PLAYLIST = 150;
    
    private Title title;
    
    private Set<Track> tracks;
    
    private Duration duration;
    
    // creation constructor
    private Playlist(
            Title title
    ) {
        super();
        this.title = title;
        this.duration = Duration.ZERO;
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
        this.tracks = new HashSet<>();
    }
    
    public final Result addTrack(Track track) {
        
        if(this.tracks.size() >= MAX_ALLOWED_TRACKS_PER_PLAYLIST)
            return Result.fail(new MaxAllowedTracksPerPlaylistExceededError());
        
        this.duration = this.duration.plus(track.getDuration());
        
        this.tracks.add(track);
        
        this.modified();
        
        return Result.ok();
    }
    
    public final void removeTrack(Track track) {
        
        if(this.tracks.isEmpty()) return;
        
        if(!this.tracks.contains(track)) return;
        
        this.duration = this.duration.minus(track.getDuration());
        
        this.modified();
        
        this.tracks.remove(track);

    }
    
    public void changeTitle(Title title) {
        this.title = title;
        this.modified();
    }
    
    public Title getTitle() {
        return this.title;
    }
    
    public Set<Track> getTracks() {
        return this.tracks;
    }
    
    public static Result create(
            Title title,
            Set<Track> tracks
    ) {
        
        Playlist instance = new Playlist(title);
        
        for(Track track : tracks) {
            
            final Result result = instance.addTrack(track);
            
            if(result.isFailure) return result;
            
        }
        
        return Result.ok(instance);
    }
    
    public static Result<Playlist> reconstitute(
            UUID id,
            String title,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            Set<Track> tracks
    ) {

        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);

        Playlist instance = new Playlist(
                idOrError.getValue(),
                titleOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue()
        );
        
        for(Track track : tracks) {
            
            final Result result = instance.addTrack(track);
            
            if(result.isFailure) return result;
            
        }
        
        return Result.ok();
    }
    
}
