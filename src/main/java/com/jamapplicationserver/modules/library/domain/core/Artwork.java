/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.nio.file.Path;
import java.time.Duration;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public abstract class Artwork extends LibraryEntity {
    
    protected RecordLabel recordLabel;
    protected RecordProducer producer;
    protected ReleaseDate releaseYear;
    protected Artist artist;
    
    // creation constructor
    protected Artwork(
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
                creatorId
        );
        this.recordLabel = recordLabel;
        this.producer = producer;
        this.releaseYear = releaseYear;
    }
    
    // reconstitution constructor
    protected Artwork(
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
                lastModifiedAt,
                creatorId,
                updaterId
        );
        this.recordLabel = recordLabel;
        this.producer = producer;
        this.releaseYear = releaseYear;
        this.artist = artist;
    }
    
    public abstract Result edit(
            Title title,
            Description description,
            TagList tags,
            GenreList genres,
            Flag flag,
            RecordLabel recordLabel,
            RecordProducer producer,
            ReleaseDate releaseYear,
            UniqueEntityId updaterId
    );
    
    public RecordLabel getRecordLabel() {
        return this.recordLabel;
    }
    
    public ReleaseDate getReleaseYear() {
        return this.releaseYear;
    }
    
    public RecordProducer getProducer() {
        return this.producer;
    }
    
    public Artist getArtist() {
        return this.artist;
    }
    
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    
}
