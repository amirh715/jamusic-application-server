/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Track;

import java.time.*;
import java.util.*;
import java.util.stream.*;
import java.nio.file.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.domain.core.events.TrackArchived;

/**
 *
 * @author amirhossein
 */
public class Track extends Artwork {
    
    private final Path audioPath;
    
    private final long size;
    
    private final MusicFileFormat type;
    
    private Lyrics lyrics;
    
    private Album album;

    // creation constructor
    private Track(
            Title title,
            Description description,
            long size,
            Duration duration,
            Path audioPath,
            MusicFileFormat type,
            Flag flag,
            TagList tags,
            GenreList genres,
            UniqueEntityId creatorId,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseDate releaseYear,
            Lyrics lyrics
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
        this.size = size;
        this.duration = duration;
        this.audioPath = audioPath;
        this.type = type;
        this.lyrics = lyrics;
    }
    
    // reconstitution constructor
    private Track(
            UniqueEntityId id,
            Title title,
            Description description,
            boolean published,
            Flag flag,
            TagList tags,
            GenreList genres,
            Path imagePath,
            long size,
            Duration duration,
            MusicFileFormat type,
            Path audioPath,
            long totalPlayedCount,
            Rate rate,
            DateTime createdAt,
            DateTime lastModifiedAt,
            UniqueEntityId creatorId,
            UniqueEntityId updaterId,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseDate releaseYear,
            Lyrics lyrics,
            Artist artist,
            Album album
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
        this.size = size;
        this.duration = duration;
        this.type = type;
        this.audioPath = audioPath;
        this.lyrics = lyrics;
        this.album = album;
    }
    
    public static final Result<Track> create(
            Title title,
            Description description,
            Flag flag,
            long size,
            Duration duration,
            Path audioPath,
            MusicFileFormat type,
            TagList tags,
            GenreList genres,
            UniqueEntityId creatorId,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseDate releaseYear,
            Lyrics lyrics
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for tracks."));
        if(audioPath == null) return Result.fail(new ValidationError("Track must have an audio path"));
        if(type == null) return Result.fail(new ValidationError("Type is required"));
        
        Track instance = new Track(
                title,
                description,
                size,
                duration,
                audioPath,
                type,
                flag,
                tags,
                genres,
                creatorId,
                recordLabel,
                producer,
                releaseYear,
                lyrics
        );

        return Result.ok(instance);
    }
    
    public static final Result<Track> reconstitute(
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
            String recordLabel,
            String producer,
            YearMonth releaseDate,
            UUID updaterId,
            String audioPath,
            String format,
            long size,
            String lyrics,
            Artist artist,
            Album album
    ) {
        
        if(title == null) return Result.fail(new ValidationError(""));
        
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flagNote);
        final Result<TagList> tagListOrError = TagList.create(tags);
        final Result<GenreList> genreListOrError = GenreList.create(genres);
        final Result<MusicFileFormat> typeOrError = MusicFileFormat.create(format);
        final Result<Rate> rateOrError = Rate.create(rate);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromUUID(creatorId);
        final Result<RecordLabel> recordLabelOrError = RecordLabel.create(recordLabel);
        final Result<RecordProducer> producerOrError = RecordProducer.create(producer);
        final Result<ReleaseDate> releaseDateOrError = ReleaseDate.create(releaseDate);
        final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromUUID(updaterId);
        final Result<Lyrics> lyricsOrError = Lyrics.create(lyrics);
        
        final ArrayList<Result> combinedProps = new ArrayList<>();
        
        combinedProps.add(idOrError);
        combinedProps.add(titleOrError);
        combinedProps.add(rateOrError);
        combinedProps.add(createdAtOrError);
        combinedProps.add(lastModifiedAtOrError);
        combinedProps.add(creatorIdOrError);
        combinedProps.add(updaterIdOrError);
        
        if(lyrics != null) combinedProps.add(lyricsOrError);
        if(recordLabel != null) combinedProps.add(recordLabelOrError);
        if(producer != null) combinedProps.add(producerOrError);
        if(releaseDate != null) combinedProps.add(releaseDateOrError);
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        Track instance = new Track(
                idOrError.getValue(),
                titleOrError.getValue(),
                description != null ? descriptionOrError.getValue() : null,
                published,
                flagNote != null ? flagOrError.getValue() : null,
                tags != null ? tagListOrError.getValue() : null,
                genres != null ? genreListOrError.getValue() : null,
                imagePath != null ? Path.of(imagePath) : null,
                size,
                Duration.ofSeconds(duration),
                typeOrError.getValue(),
                Path.of(audioPath),
                totalPlayedCount,
                rateOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                creatorIdOrError.getValue(),
                updaterIdOrError.getValue(),
                recordLabel != null ? recordLabelOrError.getValue() : null,
                producer != null ? producerOrError.getValue() : null,
                releaseDate != null ? releaseDateOrError.getValue() : null,
                lyrics != null ? lyricsOrError.getValue() : null,
                artist,
                album
        );
        
        // track's genres must be a substitute of its artist/album
        // remove those that are not
        if(instance.genres != null) {
            
            if(instance.album != null) {
                instance.genres = instance.album.getGenres();
            }
            
            if(instance.album == null && instance.artist != null) {
                
                if(instance.artist.getGenres() != null) {
                    
                    instance.genres.getValue()
                            .removeIf(trackGenre ->
                                    instance.artist.getGenres().getValue()
                                    .stream()
                                    .allMatch(artistGenre ->
                                            !artistGenre.equals(trackGenre) &&
                                            !artistGenre.isSubGenreOf(trackGenre)
                                    )
                            );
                    
                }
                
            }
            
        }
        
        return Result.ok(instance);
    }
    
    @Override
    public final void publish() {
        if(isPublished()) return;
        if(
                album != null && !album.isPublished() ||
                artist != null && !artist.isPublished()
        ) return;
        published = true;
        modified();
        
    }
    
    @Override
    public final void publish(UniqueEntityId updaterId) {
        publish();
        this.updaterId = updaterId;
    }
    
    @Override
    public final void archive() {
        if(!isPublished()) return;
        published = false;
        modified();
    }
    
    @Override
    public final void archive(UniqueEntityId updaterId) {
        archive();
        addDomainEvent(new TrackArchived(this));
        this.updaterId = updaterId;
    }
    
    /**
     * Edit single track.
     * @param title
     * @param description
     * @param tags
     * @param genres
     * @param flag
     * @param recordLabel
     * @param producer
     * @param releaseDate
     * @param updaterId
     * @return 
     */
    @Override
    public Result edit(
            Title title,
            Optional<Description> description,
            Optional<TagList> tags,
            Optional<GenreList> genres,
            Optional<Flag> flag,
            Optional<RecordLabel> recordLabel,
            Optional<RecordProducer> producer,
            Optional<ReleaseDate> releaseDate,
            UniqueEntityId updaterId
    ) {
        
        if(title != null && !title.equals(this.title)) {
            this.title = title;
            modified();
        }
        
        if(description != null) {
            description.ifPresentOrElse(
                    newValue -> {
                        if(1==1) {
                            this.description = newValue;
                            modified();
                        }
                    },
                    () -> {
                        this.description = null;
                        modified();
                    }
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
                    () -> {
                        this.flag = null;
                        modified();
                    }
            );
        }
        
        if(tags != null) {
            tags.ifPresentOrElse(
                    newValue -> {
                        if(newValue.equals(this.tags)) {
                            this.tags = newValue;
                            modified();
                        }
                    },
                    () -> {
                        this.tags = null;
                        modified();
                    }
            );
        }
        
        if(genres != null) {
            if(genres.isPresent()) {
                
                if(album != null) {
                    this.genres = album.getGenres();
                }

                if(album == null && artist != null) {

                    final boolean areTracksGenresThoseOfArtistsGenres =
                            genres.get().getValue()
                            .stream()
                            .allMatch(albumGenre -> {
                                    if(artist.getGenres() != null) {
                                        return artist.getGenres().getValue()
                                        .stream()
                                        .anyMatch(artistGenre ->
                                                artistGenre.equals(albumGenre) ||
                                                artistGenre.isSubGenreOf(albumGenre)
                                        );
                                    }
                                    return false;
                            });
                    if(!areTracksGenresThoseOfArtistsGenres)
                        return Result.fail(new GenresMustMatchASubsetOfArtistsOrAlbumsGenresError());

                }

                this.genres = genres.get();
                modified();
                
            } else {
                if(this.genres != null) {
                    this.genres = null;
                    modified();
                }
            }

        }
        
        if(recordLabel != null) {
            recordLabel.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.recordLabel)) {
                            this.recordLabel = newValue;
                            modified();
                        }
                    },
                    () -> {
                        if(this.recordLabel != null) {
                            this.recordLabel = null;
                            modified();
                        }
                    }
            );
        }
        
        if(producer != null) {
            producer.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.producer)) {
                            this.producer = newValue;
                            modified();
                        }
                    },
                    () -> {
                        if(this.producer != null) {
                            this.producer = null;
                            modified();
                        }
                    }
            );
        }
        
        if(releaseDate != null) {
            releaseDate.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(releaseDate)) {
                            this.releaseDate = newValue;
                            modified();
                        }
                    },
                    () -> {
                        if(this.releaseDate != null) {
                            this.releaseDate = null;
                            modified();
                        }
                    }
            );
        }

        this.updaterId = updaterId;
        
        return Result.ok();
    }
    
    /**
     * Edit album track.
     * @param genres
     * @param recordLabel
     * @param producer
     * @param releaseDate
     * @param updaterId 
     */
    public final void edit(
            GenreList genres,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseDate releaseDate,
            UniqueEntityId updaterId
    ) {
        
        this.genres = genres;
        this.recordLabel = recordLabel;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.updaterId = updaterId;
        
    }
    
    public void changeLyrics(Optional<Lyrics> lyrics) {
        lyrics.ifPresentOrElse(
            newValue -> {
                if(!newValue.equals(this.lyrics)) {
                    this.lyrics = newValue;
                    modified();
                }
            },
            () -> {
                if(this.lyrics != null) {
                    this.lyrics = null;
                    modified();
                }
            }
        );
    }

    public final long getSize() {
        return this.size;
    }
    
    public final MusicFileFormat getType() {
        return this.type;
    }
    
    public final Path getAudioPath() {
        return this.audioPath;
    }
    
    public final Lyrics getLyrics() {
        return this.lyrics;
    }
    
    public Album getAlbum() {
        return this.album;
    }
    
    public boolean isSingleTrack() {
        return album == null;
    }
    
    public boolean isAlbumTrack() {
        return album != null;
    }
    
    public final void played() {
        totalPlayedCount++;
    }
    
    // not related to the domain!
    // for passing album reference only!
    public void setAlbum(Album album) {
        this.album = album;
    }

}
