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
public class Album extends Artwork {
    
    public static final int MAX_ALLOWED_TRACKS_PER_ALBUM = 50;

    private Set<Track> tracks;
    
    // creation constructor
    private Album(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            UniqueEntityId creatorId,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseYear releaseYear,
            Artist artist
    ) {
        super(
            title,
            description,
            flag,
            tags,
            genres,
            creatorId,
            recordLabel,
            producer,
            releaseYear,
            artist
        );
        this.tracks = new HashSet<>();
    }
    
    // creation constructor
    private Album(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            UniqueEntityId creatorId,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseYear releaseYear,
            Artist artist,
            Set<Track> tracks
    ) {
        super(
                title,
                description,
                flag,
                tags,
                genres,
                creatorId,
                recordLabel,
                producer,
                releaseYear,
                artist
        );
        this.tracks = tracks != null ? tracks : new HashSet<>();
    }
    
    // reconstitution constructor
    private Album(
            UniqueEntityId id,
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
            UniqueEntityId creatorId,
            UniqueEntityId updaterId,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseYear releaseYear,
            Artist artist,
            Set<Track> tracks
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
            updaterId,
            recordLabel,
            producer,
            releaseYear,
            artist
        );
        this.tracks = tracks != null ? tracks : new HashSet<>();
    }
    
    public static final Result<Album> create(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            UniqueEntityId creatorId,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseYear releaseYear,
            Artist artist
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for albums."));
        
        Album instance = new Album(
                title,
                description,
                flag,
                tags,
                genres,
                creatorId,
                recordLabel,
                producer,
                releaseYear,
                artist
        );
        
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
            long totalPlayedCount,
            double rate,
            long duration,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            UUID creatorId,
            UUID updaterId,
            String recordLabel,
            String producer,
            Integer releaseYear,
            Artist artist,
            Set<Track> tracks
    ) {
        
        final ArrayList<Result> combinedProps = new ArrayList<>();
        
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flagNote);
        final Result<TagList> tagListOrError = TagList.create(tags);
        final Result<GenreList> genreListOrError = GenreList.create(genres);
        final Result<Rate> rateOrError = Rate.create(rate);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromUUID(creatorId);
        final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromUUID(updaterId);
        final Result<RecordLabel> recordLabelOrError = RecordLabel.create(recordLabel);
        final Result<RecordProducer> producerOrError = RecordProducer.create(producer);
        final Result<ReleaseYear> releaseYearOrError = ReleaseYear.create(releaseYear);
        
        combinedProps.add(idOrError);
        combinedProps.add(titleOrError);
        combinedProps.add(creatorIdOrError);
        combinedProps.add(updaterIdOrError);
        
        if(description != null) combinedProps.add(descriptionOrError);
        if(flagNote != null) combinedProps.add(flagOrError);
        if(tags != null) combinedProps.add(tagListOrError);
        if(genres != null) combinedProps.add(genreListOrError);
        if(recordLabel != null) combinedProps.add(recordLabelOrError);
        if(producer != null) combinedProps.add(producerOrError);
        if(releaseYear != null) combinedProps.add(releaseYearOrError);
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        Album instance = new Album(
                idOrError.getValue(),
                titleOrError.getValue(),
                description != null ? descriptionOrError.getValue() : null,
                published,
                flagNote != null ? flagOrError.getValue() : null,
                tags != null ? tagListOrError.getValue() : null,
                genres != null ? genreListOrError.getValue() : null,
                imagePath != null ? Path.of(imagePath) : null,
                Duration.ofSeconds(duration),
                totalPlayedCount,
                rateOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                creatorIdOrError.getValue(),
                updaterIdOrError.getValue(),
                recordLabel != null ? recordLabelOrError.getValue() : null,
                producer != null ? producerOrError.getValue() : null,
                releaseYear != null ? releaseYearOrError.getValue() : null,
                artist,
                tracks
        );
        
        // set tracks album
        if(tracks != null)
            tracks.forEach(track -> track.setAlbum(instance));

        //
        if(
                artist != null && artist.getGenres() != null
        ) {
            
            // album's genres must be a subset of album's artist genres
            for(Genre genre : artist.getGenres().getValue()) {

                final boolean doesGenreMatchOneOfArtistsGenres =
                        artist.getGenres()
                        .getValue()
                        .stream()
                        .anyMatch(genreOfArtist -> {
                            return
                                    genreOfArtist.equals(genre) ||
                                    genreOfArtist.isSubGenreOf(genre);
                        });

                if(!doesGenreMatchOneOfArtistsGenres)
                    return Result.fail(new GenresMustMatchASubsetOfArtistsOrAlbumsGenresError());

            }

        }
        
        return Result.ok(instance);
    }
    
    @Override
    public void publish() {
        if(this.isPublished()) return;
        if(this.tracks.isEmpty() || !this.artist.isPublished())
            this.archive();
        else {
            this.published = true;
            this.tracks.forEach(track -> track.publish());
            this.modified();
        }
    }
    
    @Override
    public void publish(UniqueEntityId updaterId) {
        publish();
        this.updaterId = updaterId;
    }
    
    @Override
    public void archive() {
        if(!this.isPublished()) return;
        published = false;
        tracks.forEach(track -> track.archive());
        modified();
    }
    
    @Override
    public void archive(UniqueEntityId updaterId) {
        this.archive();
        this.updaterId = updaterId;
    }
    
    @Override
    public void rate(Rate rate) {
        this.rate = rate;
    }
    
    @Override
    public Result edit(
            Title title,
            Description description,
            TagList tags,
            GenreList genres,
            Flag flag,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseYear releaseYear,
            UniqueEntityId updaterId
    ) {
        
        this.title = title != null ? title : this.title;
        this.description = description != null ? description : this.description;
        this.tags = tags != null ? tags : this.tags;
        this.flag = flag != null ? flag : this.flag;
        this.recordLabel = recordLabel != null ? recordLabel : this.recordLabel;
        this.producer = producer != null ? producer : this.producer;
        this.releaseYear = releaseYear != null ? releaseYear : this.releaseYear;

        if(genres != null) {
            // album's genres must be a subset of album's artist genres
            for(Genre genre : genres.getValue()) {

                final boolean doesGenreMatchOneOfArtistsGenres =
                        artist.getGenres()
                        .getValue()
                        .stream()
                        .anyMatch(genreOfArtist -> {
                            return
                                    genreOfArtist.equals(genre) ||
                                    genreOfArtist.isSubGenreOf(genre);
                        });

                if(!doesGenreMatchOneOfArtistsGenres)
                    return Result.fail(new GenresMustMatchASubsetOfArtistsOrAlbumsGenresError());

            }
        }
        
        this.genres = genres;

        for(Track track : tracks) {
            track.setAlbum(this);
            final Result result = track.edit(
                    track.getTitle(),
                    track.getDescription(),
                    track.getTags(),
                    genres,
                    track.getFlag(),
                    recordLabel,
                    producer,
                    releaseYear,
                    updaterId
            );
            if(result.isFailure) return result;
        }
            
        this.updaterId = updaterId;
        modified();
        
        return Result.ok();
    }
    
    public final Result addTrack(Track track, UniqueEntityId updaterId) {
        
        final boolean alreadyExists = this.tracks.contains(track);
        if(alreadyExists) return Result.ok();
        
        if(tracks.size() >= MAX_ALLOWED_TRACKS_PER_ALBUM)
                return Result.fail(new AlbumMaxAllowedTracksExceededError());
        
        track.setAlbum(this);
        track.setArtist(artist);
        
        track.changeImage(getImagePath(), updaterId);
        track.edit(
                track.getTitle(),
                track.getDescription(),
                track.getTags(),
                getGenres(),
                track.getFlag(),
                recordLabel,
                producer,
                releaseYear,
                updaterId
        );
        
        if(isPublished())
            track.publish();
        else
            track.archive();
        
        duration = duration.plus(track.getDuration());
        
        tracks.add(track);
        
        this.updaterId = updaterId;
        modified();
        
        return Result.ok();
    }
    
    public final Result removeTrack(Track track, UniqueEntityId updaterId) {
        
        if(!tracks.contains(track)) return Result.ok();
        
        duration = duration.minus(track.getDuration());
        
        tracks.remove(track);
        
        modified();
        
        return Result.ok();
    }
    
    public final Set<Track> getTracks() {
        return tracks;
    }
    
    public final Artist getArtist() {
        return artist;
    }
    
    @Override
    public final void changeImage(Path path, UniqueEntityId updaterId) {
        imagePath = path;
        tracks.forEach(track -> track.changeImage(path, updaterId));
        this.updaterId = updaterId;
        modified();
    }
    
    @Override
    public final void removeImage(UniqueEntityId updaterId) {
        imagePath = null;
        tracks.forEach(track -> track.removeImage(updaterId));
        this.updaterId = updaterId;
        modified();
    }
    
}
