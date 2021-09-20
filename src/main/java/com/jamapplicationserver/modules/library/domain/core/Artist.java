/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.*;
import java.nio.file.Path;
import java.time.Duration;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author amirhossein
 */
public abstract class Artist extends LibraryEntity {
    
    public static final int MAX_ALLOWED_TRACKS_PER_ARTIST = 150;
    public static final int MAX_ALLOWED_ALBUMS_PER_ARTIST = 150;
    public static final Duration MAX_ALLOWED_DURATION_PER_ARTIST = Duration.ofHours(12);
    
    protected Set<UniqueEntityId> albumsIds;
    protected Set<UniqueEntityId> tracksIds;
    protected InstagramId instagramId;
    
    // creation constructor
    protected Artist(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            InstagramId instagramId,
            UniqueEntityId creatorId
    ) {
        super(title, description, flag, tags, genres, creatorId);
        this.albumsIds = new HashSet<>();
        this.tracksIds = new HashSet<>();
        this.instagramId = instagramId;
    }
    
    // reconstitution construcutor
    protected Artist(
            UniqueEntityId id,
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
            UniqueEntityId creatorId,
            UniqueEntityId updaterId,
            Set<UniqueEntityId> albumsIds,
            Set<UniqueEntityId> tracksIds
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
                lastModifiedAt,
                creatorId,
                updaterId
        );
        this.albumsIds = albumsIds != null ? albumsIds : new HashSet<>();
        this.tracksIds = tracksIds != null ? tracksIds : new HashSet<>();
        this.instagramId = instagramId;
    }

    public final Result edit(
            Title title,
            Description description,
            TagList tags,
            GenreList genres,
            Flag flag,
            InstagramId instagramId,
            UniqueEntityId updaterId
    ) {
        
        this.title = title != null ? title : this.title;
        this.description = description != null ? description : this.description;
        this.tags = tags != null ? tags : this.tags;
        this.genres = genres != null ? genres : this.genres;
        this.flag = flag != null ? flag : this.flag;
        this.instagramId = instagramId != null ? instagramId : this.instagramId;
        this.updaterId = updaterId;
        
        modified();
        
        return Result.ok();
    }
    
    public void removeInstagramId() {
        this.instagramId = null;
    }
    
    @Override
    public final void publish() {
        if(this.tracksIds.isEmpty() && this.albumsIds.isEmpty()) {
            this.archive();
            return;
        }
        if(this.isPublished()) return;
        this.published = true;
        this.modified();
        // domain event ??
    }
    
    @Override
    public final void publish(UniqueEntityId updaterId) {
        this.publish();
        this.updaterId = updaterId;
    }
    
    @Override
    public final void archive() {
        if(!this.isPublished()) return;
        this.published = false;
        this.modified();
        // domain event ??
    }
    
    @Override
    public final void archive(UniqueEntityId updaterId) {
        this.archive();
        this.updaterId = updaterId;
    }
    
    @Override
    public void rate(Rate rate) {
        this.rate = rate;
    }
    
    public Result addTrack(Track track, UniqueEntityId updaterId) {
        
        if(this.tracksIds.size() >= MAX_ALLOWED_TRACKS_PER_ARTIST)
            return Result.fail(new ArtistMaxAllowedTracksExceededError());
        
        final boolean willDurationBeOverTheLimit =
                !this.duration
                        .plus(track.duration)
                        .minus(MAX_ALLOWED_DURATION_PER_ARTIST)
                        .isNegative();
        if(willDurationBeOverTheLimit)
            return Result.fail(new ArtistMaxAllowedDurationExceededError());
        
        final boolean alreadyExists = this.tracksIds.contains(track.id);
        
        if(alreadyExists) return Result.ok();
        
        if(this.isPublished())
            track.publish();
        else
            track.archive();
        
        this.duration = this.duration.plus(track.duration);
        
        this.tracksIds.add(track.id);
        track.setArtist(this);
        
        this.updaterId = updaterId;
        this.modified();
        
        return Result.ok();
    }
    
    public Result removeTrack(Track track, UniqueEntityId updaterId) {
        
        this.duration = this.duration.minus(track.duration);
        
        this.tracksIds.remove(track.id);
        
        this.updaterId = updaterId;
        this.modified();
        
        return Result.ok();
    }
    
    public Result addAlbum(Album album, UniqueEntityId updaterId) {
        
        if(this.albumsIds.size() >= MAX_ALLOWED_ALBUMS_PER_ARTIST)
            return Result.fail(new ArtistMaxAllowedAlbumsExceededError());
        
        final boolean alreadyExists = this.albumsIds.contains(album.id);
        
        if(alreadyExists) return Result.ok();
        
        if(this.isPublished())
            album.publish();
        else
            album.archive();
        
        this.duration = this.duration.plus(album.duration);
        
        this.albumsIds.add(album.id);
        
        this.modified();
        
        return Result.ok();
    }
    
    public Result removeAlbum(Album album, UniqueEntityId updaterId) {
        
        this.duration = this.duration.minus(album.duration);
        
        this.albumsIds.remove(album.id);
        
        this.updaterId = updaterId;
        this.modified();
        
        return Result.ok();
    }
    
    public Set<UniqueEntityId> getAlbumsIds() {
        return this.albumsIds;
    }
    
    public Set<UniqueEntityId> getTracksIds() {
        return this.tracksIds;
    }
    
    public final InstagramId getInstagramId() {
        return this.instagramId;
    }

    
}
