/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Track;

import java.time.*;
import java.util.*;
import java.nio.file.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Track extends LibraryEntity {
    
    private final Path audioPath;
    
    private final long size;
    
    private final MusicFileFormat type;
    
    private Lyrics lyrics;

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
            Lyrics lyrics
    ) {
        super(
                title,
                description,
                flag,
                tags,
                genres
        );
        this.size = size;
        this.duration = duration;
        this.audioPath = audioPath;
        this.type = type;
        this.lyrics = lyrics;
    }
    
    // reconstitution constructor
    private Track(
            UniqueEntityID id,
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
            Lyrics lyrics
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
        this.size = size;
        this.duration = duration;
        this.type = type;
        this.audioPath = audioPath;
        this.lyrics = lyrics;
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
            Lyrics lyrics
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for tracks."));
        
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
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            long duration,
            long size,
            String format,
            String audioPath,
            long totalPlayedCount,
            double rate,
            String lyrics,
            Artist artist
    ) {
        
        if(title == null) return Result.fail(new ValidationError(""));
        
        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flagNote);
        final Result<TagList> tagListOrError = TagList.create(tags);
        final Result<GenreList> genreListOrError = GenreList.create(genres);
        final Result<MusicFileFormat> typeOrError = MusicFileFormat.create(format);
        final Result<Rate> rateOrError = Rate.create(rate);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<Lyrics> lyricsOrError = Lyrics.create(lyrics);
        
        final Result[] combinedProps = {
            idOrError,
            titleOrError,
            descriptionOrError,
            flagOrError,
            tagListOrError,
            genreListOrError,
            rateOrError,
            typeOrError,
            createdAtOrError,
            lastModifiedAtOrError,
            lyricsOrError
        };
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        Track instance = new Track(
                idOrError.getValue(),
                titleOrError.getValue(),
                descriptionOrError.getValue(),
                published,
                flagOrError.getValue(),
                tagListOrError.getValue(),
                genreListOrError.getValue(),
                Path.of(imagePath),
                size,
                Duration.ofSeconds(duration),
                typeOrError.getValue(),
                Path.of(audioPath),
                totalPlayedCount,
                rateOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                lyricsOrError.getValue()
        );
        
        return Result.ok(instance);
    }
    
    @Override
    public final void publish() {
        this.published = true;
    }
    
    @Override
    public final void archive() {
        this.published = false;
    }
    
    @Override
    public void edit(
            Title title,
            Description description,
            TagList tags,
            GenreList genres
    ) {
        
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.genres = genres;

    }
    
    public void changeLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
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
    
    public final void playedBy(UniqueEntityID playerId, DateTime playedAt) {
        
        this.totalPlayedCount++;
        
    }

}
