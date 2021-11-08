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
import com.jamapplicationserver.modules.library.domain.core.events.*;

/**
 *
 * @author amirhossein
 */
public abstract class Artist extends LibraryEntity {
    
    public static final int MAX_ALLOWED_SINGLE_TRACKS_PER_ARTIST = 150;
    public static final int MAX_ALLOWED_ALBUMS_PER_ARTIST = 150;
    
    protected Set<UniqueEntityId> albumsIds = new HashSet<>();
    protected Set<UniqueEntityId> allTracksIds = new HashSet<>();
    protected Set<UniqueEntityId> singleTracksIds = new HashSet<>();
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
        this.allTracksIds = new HashSet<>();
        this.singleTracksIds = new HashSet<>();
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
            Set<UniqueEntityId> allTracksIds,
            Set<UniqueEntityId> singleTracksIds
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
        this.albumsIds = albumsIds;
        this.allTracksIds = allTracksIds;
        this.singleTracksIds = singleTracksIds;
        this.instagramId = instagramId;
    }

    /**
     * Edit artist info.
     * Notice: If a nullable (optional) parameter is null, the field won't be
     * edited. If the parameter is an empty "Optional" instance, the field value
     * will be set to null. If a non-empty "Optional" instance is given, the field
     * value will be set.
     * @param title
     * @param description
     * @param tags
     * @param genres
     * @param flag
     * @param instagramId
     * @param updaterId
     * @return Result
     */
    public final Result edit(
            Title title,
            Optional<Description> description,
            Optional<TagList> tags,
            Optional<GenreList> genres,
            Optional<Flag> flag,
            Optional<InstagramId> instagramId,
            UniqueEntityId updaterId
    ) {

        if(title != null && !title.equals(this.title)) {
            this.title = title;
            modified();
        }
        
        if(description != null) {
            description
                    .ifPresentOrElse(
                            newValue -> {
                                if(!newValue.equals(this.description)) {
                                    this.description = newValue;
                                    modified();
                                }
                            },
                            () -> this.description = null
                    );
        }
        
        if(tags != null) {
            tags.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.tags)) {
                            this.tags = newValue;
                            modified();
                        }
                    },
                    () -> this.tags = null
            );
        }
        
        if(genres != null) {
            genres.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.genres)) {
                            this.genres = newValue;
                            modified();
                        }
                    },
                    () -> this.genres = null
            );
        }

        if(flag != null) {
            flag.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.flag)) {
                            this.flag = newValue;
                            modified();
                        }
                    },
                    () -> this.flag = null
            );
        }
        
        if(instagramId != null) {
            instagramId.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.instagramId)) {
                            this.instagramId = newValue;
                            modified();
                        }
                    },
                    () -> this.instagramId = null
            );
        }
        
        addDomainEvent(new ArtistEdited(this));
        
        return Result.ok();
    }
    
    @Override
    protected final void publish() {
        if(allTracksIds.isEmpty()) {
            archive();
            return;
        }
        if(isPublished()) return;
        this.published = true;
        modified();
        
    }
    
    @Override
    public final void publish(UniqueEntityId updaterId) {
        publish();
        this.updaterId = updaterId;
    }
    
    public final void publish(UniqueEntityId updaterId, Boolean cascadeToArtworks) {
        publish(updaterId);
        addDomainEvent(new ArtistPublished(this, cascadeToArtworks));
    }
    
    @Override
    public final void archive() {
        if(!isPublished()) return;
        this.published = false;
        modified();
        
    }
    
    @Override
    public final void archive(UniqueEntityId updaterId) {
        archive();
        this.updaterId = updaterId;
        addDomainEvent(new ArtistArchived(this));
    }
    
    public Result addArtwork(Artwork artwork, UniqueEntityId updaterId) {

        // check if artwork genres match those of the artist's
        // if artist has genres
        if(genres != null) { 
            
            // if the artwork being added has genres
            if(artwork.getGenres() != null) {
                
                final boolean doesArtworksGenresMatchThoseOfTheArtists =
                        artwork.getGenres().getValue()
                        .stream()
                        .allMatch(artworkGenre ->
                                genres.getValue()
                                .stream()
                                .anyMatch(artistGenre ->
                                        artistGenre.equals(artworkGenre) ||
                                        artistGenre.isSubGenreOf(artworkGenre)
                                )
                        );
                
                if(!doesArtworksGenresMatchThoseOfTheArtists)
                    return Result.fail(new GenresMustMatchASubsetOfArtistsOrAlbumsGenresError());
                
            }
            
        }
        
        if(artwork instanceof Album) {
            
            if(albumsIds.size() >= MAX_ALLOWED_ALBUMS_PER_ARTIST)
                return Result.fail(new ArtistMaxAllowedAlbumsExceededError());
            
            final boolean alreadyExists = albumsIds.contains(artwork.id);
            if(alreadyExists) return Result.ok();
            
            if(!isPublished() && artwork.isPublished()) return null;
            
            duration = duration.plus(artwork.duration);
            
            albumsIds.add(artwork.id);
            ((Album) artwork).getTracks()
                    .forEach(track -> allTracksIds.add(track.id));

        }
        
        if(artwork instanceof Track) {
            
            if(singleTracksIds.size() >= MAX_ALLOWED_SINGLE_TRACKS_PER_ARTIST)
                return null;
            
            final boolean alreadyExists = singleTracksIds.contains(artwork.id);
            if(alreadyExists) return null;
            
            if(!isPublished() && artwork.isPublished());
            
            duration = duration.plus(artwork.duration);
            
            singleTracksIds.add(artwork.id);
            allTracksIds.add(artwork.id);

        }
        
        this.updaterId = updaterId;
        
        modified();
        
        return Result.ok();
    }
    
    public Set<UniqueEntityId> getAlbumsIds() {
        return this.albumsIds;
    }
    
    public Set<UniqueEntityId> getAllTracksIds() {
        return this.allTracksIds;
    }
    
    public Set<UniqueEntityId> getSingleTracksIds() {
        return this.singleTracksIds;
    }
    
    public final InstagramId getInstagramId() {
        return this.instagramId;
    }

    
}
