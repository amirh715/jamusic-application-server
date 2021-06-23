/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Album;

import java.util.*;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author amirhossein
 */
public class Album extends LibraryEntity {
    
    public static final int MAX_ALLOWED_TRACKS_PER_ALBUM = 30;

    private Set<Track> tracks = new HashSet<>();
    
    // creation constructor
    private Album(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres
    ) {
        super(
            title,
            description,
            flag,
            tags,
            genres
        );
    }
    
    private Album(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            Set<Track> tracks
    ) {
        super(
                title,
                description,
                flag,
                tags,
                genres
        );
        this.tracks = tracks;
    }
    
    // reconstitution constructor
    private Album(
            UniqueEntityID id,
            Title title,
            Description description,
            boolean published,
            Flag flag,
            TagList tags,
            GenreList genres,
            Path imagePath,
            Duration duration,
            long totalPlayedCount,
            Rate rate,
            DateTime createdAt,
            DateTime lastModifiedAt,
            Set<Track> tracks,
            Artist artist
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
        this.tracks = tracks;
    }
    
    public static final Result<Album> create(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for albums."));
        
        Album instance = new Album(
                title,
                description,
                flag,
                tags,
                genres
        );
        
        return Result.ok(instance);
        
    }
    
    public static final Result<Album> create(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            Set<Track> tracks
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for albums."));

        Album instance = new Album(
                title,
                description,
                flag,
                tags,
                genres
        );
        
        for(Track track : tracks) {
            
            final Result result = instance.addTrack(track);
            
            if(result.isFailure) return result;
            
        }

        return Result.ok(instance);
    }
    
    public static final Result<Album> reconstitute(
            UUID id,
            String title,
            String description,
            boolean published,
            String flagNote,
            Set<Tag> tags,
            Set<Genre> genres,
            String imagePath,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            long totalPlayedCount,
            double rate,
            long duration,
            Set<Track> tracks,
            Artist artist
    ) {
        
        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flagNote);
        final Result<TagList> tagListOrError = TagList.create(tags);
        final Result<GenreList> genreListOrError = GenreList.create(genres);
        final Result<Rate> rateOrError = Rate.create(rate);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        
        final Result[] combinedProps = {
            idOrError,
            titleOrError,
            descriptionOrError,
            flagOrError,
            tagListOrError,
            genreListOrError,
            rateOrError,
            createdAtOrError,
            lastModifiedAtOrError
        };
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        Album instance = new Album(
                idOrError.getValue(),
                titleOrError.getValue(),
                descriptionOrError.getValue(),
                published,
                flagOrError.getValue(),
                tagListOrError.getValue(),
                genreListOrError.getValue(),
                Path.of(imagePath),
                Duration.ofSeconds(duration),
                totalPlayedCount,
                rateOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                tracks,
                artist
        );
        
        return Result.ok(instance);
    }
    
    @Override
    public void publish() {
        if(this.tracks.isEmpty())
            this.archive();
        else {
            this.published = true;
            this.tracks.forEach(track -> track.publish());
        }
    }
    
    @Override
    public void archive() {
        this.published = false;
        this.tracks.forEach(track -> track.archive());
    }
    
    public final Result addTrack(Track track) {
        
        if(this.tracks.size() > MAX_ALLOWED_TRACKS_PER_ALBUM)
                return Result.fail(new AlbumMaxAllowedTracksExceededError());
        
        final boolean alreadyExists = this.tracks.contains(track);
        
        if(alreadyExists) return Result.ok();
        
        if(this.isPublished())
            track.publish();
        else
            track.archive();
        
        this.duration.plus(track.getDuration());
        
        this.tracks.add(track);
        
        return Result.ok();
    }
    
    public final Result removeTrack(Track track) {
        
        this.duration.minus(track.getDuration());
        
        this.tracks.remove(track);
        
        return Result.ok();
    }
    
    public final Set<Track> getTracks() {
        return this.tracks;
    }
    
    @Override
    public final void changeImage(Path path) {
        this.imagePath = path;
        this.tracks.forEach(track -> track.changeImage(path));
    }
    
    @Override
    public final void removeImage() {
        this.imagePath = null;
        this.tracks.forEach(track -> track.removeImage());
    }
    
    @Override
    public final void flag(Flag flag) {
        this.flag = flag;
        this.tracks.forEach(track -> track.flag(flag));
    }
    
    @Override
    public final void unflag() {
        this.flag = null;
        this.tracks.forEach(track -> track.unflag());
    }
    
}
