/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.*;
import java.nio.file.Path;
import java.time.Duration;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import java.util.stream.Collectors;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author amirhossein
 */
public abstract class Artist extends LibraryEntity {
    
    public static final int MAX_ALLOWED_TRACKS_PER_ARTIST = 500;
    public static final int MAX_ALLOWED_ALBUMS_PER_ARTIST = 500;
    
    protected Set<UniqueEntityID> albumsIds = new HashSet<>();
    protected Set<UniqueEntityID> tracksIds = new HashSet<>();
    protected InstagramId instagramId;
    
    // creation constructor
    protected Artist(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            InstagramId instagramId
    ) {
        super(title, description, flag, tags, genres);
        this.instagramId = instagramId;
    }
    
    // reconstitution construcutor
    protected Artist(
            UniqueEntityID id,
            Title title,
            Description description,
            boolean published,
            Flag flag,
            TagList tags,
            GenreList genres,
            Path imagePath,
            InstagramId instagramId,
            Duration duration,
            long totalPlayedCount,
            Rate rate,
            DateTime createdAt,
            DateTime lastModifiedAt,
            Set<UniqueEntityID> albumsIds,
            Set<UniqueEntityID> tracksIds
    ) {
        super(
                id,
                title,
                description,
                published,
                flag,
                tags,
                genres,
                imagePath,
                duration,
                totalPlayedCount,
                rate,
                createdAt,
                lastModifiedAt
        );
        this.albumsIds = albumsIds;
        this.tracksIds = tracksIds;
        this.instagramId = instagramId;
    }
    
    public Result edit(
            Title title,
            Description description,
            TagList tags,
            GenreList genres,
            InstagramId instagramId
    ) {
        
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.genres = genres;
        this.instagramId = instagramId;
        
        return Result.ok();
    }
    
    @Override
    public final void publish() {
        if(this.tracksIds.isEmpty() && this.albumsIds.isEmpty())
            this.archive();
        else
            this.published = true;
    }
    
    @Override
    public final void archive() {
        this.published = false;
    }
    
    public Result addTrack(Track track) {
        
        if(this.tracksIds.size() > MAX_ALLOWED_TRACKS_PER_ARTIST)
            return Result.fail(new ArtistMaxAllowedTracksExceededError());
        
        final boolean alreadyExists = this.tracksIds.contains(track.id);
        
        if(alreadyExists) return Result.ok();
        
        if(this.isPublished())
            track.publish();
        else
            track.archive();
        
        this.duration.plus(track.duration);
        
        this.tracksIds.add(track.id);
        
        return Result.ok();
    }
    
    public Result removeTrack(Track track) {
        
        this.duration.minus(track.duration);
        
        this.tracksIds.remove(track.id);
        
        return Result.ok();
    }
    
    public Result addAlbum(Album album) {
        
        if(this.albumsIds.size() > MAX_ALLOWED_ALBUMS_PER_ARTIST)
            return Result.fail(new ArtistMaxAllowedAlbumsExceededError());
        
        final boolean alreadyExists = this.albumsIds.contains(album.id);
        
        if(alreadyExists) return Result.ok();
        
        if(this.isPublished())
            album.publish();
        else
            album.archive();
        
        this.duration.plus(album.duration);
        
        this.albumsIds.add(album.id);
        
        return Result.ok();
    }
    
    public Result removeAlbum(Album album) {
        
        this.duration.minus(album.duration);
        
        this.albumsIds.remove(album.id);
        
        return Result.ok();
    }
    
    public Set<UniqueEntityID> getAlbumsIds() {
        return this.albumsIds;
    }
    
    public Set<UniqueEntityID> getTracksIds() {
        return this.tracksIds;
    }
    
    public final InstagramId getInstagramId() {
        return this.instagramId;
    }

    
}
