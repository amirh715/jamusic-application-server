/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Playlist;

import java.util.Set;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author amirhossein
 */
public class Playlist extends AggregateRoot {
    
    private static final int MAX_ALLOWED_TRACKS_PER_PLAYLIST = 150;
    
    public Title title;
    
    public Set<Track> tracks;
    
    private Playlist(
            UniqueEntityID id,
            Title title
    ) {
        super(id);
        this.title = title;
    }
    
    private Playlist(
            Title title
    ) {
        super(new UniqueEntityID());
        this.title = title;
    }
    
    public static Result create(
            Title title,
            Set<Track> tracks
    ) {
        
        Playlist instance = new Playlist(title);
        
        final Result replaceTracksOrError = instance.replaceTracks(tracks);
        
        if(replaceTracksOrError.isFailure) return replaceTracksOrError;
        
        return Result.ok(instance);
    }
    
    public static Result reconstitute(
            UniqueEntityID id,
            Title title,
            Set<Track> tracks
    ) {
        
        Playlist instance = new Playlist(id, title);
        
        final Result replaceTracksOrError = instance.replaceTracks(tracks);
        
        if(replaceTracksOrError.isFailure) return replaceTracksOrError;
        
        return Result.ok(instance);
    }
    
    public Result replaceTracks(Set<Track> tracks) {
        
        if(tracks.size() > MAX_ALLOWED_TRACKS_PER_PLAYLIST)
            return Result.fail(new ValidationError("Playlists can have " + MAX_ALLOWED_TRACKS_PER_PLAYLIST + " tracks max."));
        
        this.tracks.retainAll(tracks);

        return Result.ok();
    }
    
}
