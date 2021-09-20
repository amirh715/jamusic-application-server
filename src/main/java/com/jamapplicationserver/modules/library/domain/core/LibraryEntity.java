/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

/**
 *
 * @author amirhossein
 */
public abstract class LibraryEntity extends AggregateRoot {
    
    protected Title title;
    
    protected Description description;
    
    protected boolean published;
    
    protected Flag flag;
    
    protected TagList tags;
    
    protected GenreList genres;
    
    protected Path imagePath;
    
    protected long totalPlayedCount;
    
    protected Duration duration;
    
    protected Rate rate;
    
    protected final UniqueEntityId creatorId;
    
    protected UniqueEntityId updaterId;
    
    // creation constructor
    protected LibraryEntity(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            UniqueEntityId creatorId
    ) {
        super();
        this.title = title;
        this.description = description;
        this.published = false;
        this.flag = flag;
        this.tags = tags;
        this.genres = genres;
        this.published = false;
        this.duration = Duration.ZERO;
        this.totalPlayedCount = 0;
        this.rate = Rate.createZero();
        this.creatorId = creatorId;
        this.updaterId = creatorId;
    }
    
    // reconstitution constructor
    protected LibraryEntity(
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
            UniqueEntityId updaterId
    ) {
        super(id, createdAt, lastModifiedAt);
        this.title = title;
        this.description = description;
        this.published = published;
        this.flag = flag;
        this.tags = tags;
        this.genres = genres;
        this.imagePath = imagePath;
        this.duration = duration;
        this.totalPlayedCount = totalPlayedCount;
        this.rate = rate;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
    }
    
    public Title getTitle() {
        return this.title;
    }
    
    public Description getDescription() {
        return this.description;
    }
    
    public Flag getFlag() {
        return this.flag;
    }
    
    public TagList getTags() {
        return this.tags;
    }
    
    public GenreList getGenres() {
        return this.genres;
    }
    
    public Path getImagePath() {
        return this.imagePath;
    }
    
    public Duration getDuration() {
        return this.duration;
    }
    
    public long getTotalPlayedCount() {
        return this.totalPlayedCount;
    }
    
    public Rate getRate() {
        return this.rate;
    }
    
    public UniqueEntityId getCreatorId() {
        return this.creatorId;
    }
    
    public UniqueEntityId getUpdaterId() {
        return this.updaterId;
    }
    
    public abstract void publish();
    
    public abstract void publish(UniqueEntityId updaterId);
    
    public abstract void archive();
    
    public abstract void archive(UniqueEntityId updaterId);
    
    public abstract void rate(Rate rate);

    public void changeImage(Path path, UniqueEntityId updaterId) {
        this.imagePath = path;
        this.updaterId = updaterId;
        modified();
    }
    
    public void removeImage(UniqueEntityId updaterId) {
        this.imagePath = null;
        this.updaterId = updaterId;
        modified();
    }
    
    public boolean hasImage() {
        return this.imagePath != null;
    }
    
    public final boolean isPublished() {
        return this.published;
    }
    
}
