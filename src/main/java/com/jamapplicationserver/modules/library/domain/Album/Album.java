/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Album;

import java.util.*;
import java.util.stream.*;
import java.nio.file.Path;
import java.time.*;
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
            ReleaseDate releaseYear
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
            releaseYear
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
            ReleaseDate releaseYear,
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
                releaseYear
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
            ReleaseDate releaseYear,
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
            ReleaseDate releaseYear
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
                releaseYear
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
            YearMonth releaseDate,
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
        final Result<ReleaseDate> releaseDateOrError = ReleaseDate.create(releaseDate);
        
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
        if(releaseDate != null) combinedProps.add(releaseDateOrError);
        
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
                releaseDate != null ? releaseDateOrError.getValue() : null,
                artist,
                tracks
        );
        
        // set tracks album
        if(tracks != null)
            tracks.forEach(track -> track.setAlbum(instance));

        if(instance.genres != null) {
            
            if(instance.artist != null && instance.artist.getGenres() != null) {
               instance.genres.getValue()
                        .removeIf(albumGenre ->
                                instance.artist.getGenres().getValue()
                                .stream()
                                .allMatch(artistGenre ->
                                        !artistGenre.equals(albumGenre) &&
                                        !artistGenre.isSubGenreOf(albumGenre)
                                )
                        );
            }
            
        }
        
        return Result.ok(instance);
    }
    
    @Override
    public void publish() {
        if(isPublished()) return;
        if(tracks.isEmpty() || !artist.isPublished())
            return;
        published = true;
        tracks.forEach(track -> {
            track.setAlbum(this);
            track.publish();
        });
        modified();
        
    }
    
    @Override
    public void publish(UniqueEntityId updaterId) {
        publish();
        this.updaterId = updaterId;
    }
    
    @Override
    public void archive() {
        if(!isPublished()) return;
        published = false;
        tracks.forEach(track -> track.archive());
        modified();
    }
    
    @Override
    public void archive(UniqueEntityId updaterId) {
        archive();
        this.updaterId = updaterId;
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
            ReleaseDate releaseYear,
            UniqueEntityId updaterId
    ) {
        
        this.title = title != null ? title : this.title;
        this.description = description != null ? description : null;
        this.tags = tags != null ? tags : null;
        this.flag = flag != null ? flag : null;
        this.recordLabel = recordLabel != null ? recordLabel : null;
        this.producer = producer != null ? producer : null;
        this.releaseYear = releaseYear != null ? releaseYear : null;

        if(genres != null) {
            
            if(artist != null && artist.getGenres() != null) {
                
                final boolean areAlbumGenresThoseOfArtistsGenres =
                        genres.getValue()
                        .stream()
                        .allMatch(albumGenre ->
                                artist.getGenres().getValue()
                                .stream()
                                .anyMatch(artistGenre ->
                                        artistGenre.equals(albumGenre) ||
                                        artistGenre.isSubGenreOf(albumGenre)
                                )
                        );
                if(!areAlbumGenresThoseOfArtistsGenres)
                    return Result.fail(new GenresMustMatchASubsetOfArtistsOrAlbumsGenresError());
                
            }
            
            this.genres = genres;
            
        }


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
        
        final boolean alreadyExists = tracks.contains(track);
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
        
        tracks.add(track);
        
        this.updaterId = updaterId;
        modified();
        
        return Result.ok();
    }
    
    public final Set<Track> getTracks() {
        return tracks;
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
