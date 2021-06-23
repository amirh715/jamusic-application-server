/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import com.jamapplicationserver.core.domain.*;
import java.nio.file.Path;
import java.time.Duration;

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
    
    protected DateTime createdAt;
    
    protected DateTime lastModifiedAt;
    
    protected long totalPlayedCount;
    
    protected Duration duration;
    
    protected Rate rate;
    
    // creation constructor
    protected LibraryEntity(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres
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
        this.createdAt = DateTime.createNow();
        this.lastModifiedAt = DateTime.createNow();
    }
    
    // reconstitution constructor
    protected LibraryEntity(
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
            DateTime lastModifiedAt
    ) {
        super(id);
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
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
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
    
    public DateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public DateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    public abstract void publish();
    
    public abstract void archive();
    
    public void changeImage(Path path) {
        this.imagePath = path;
    }
    
    public void removeImage() {
        this.imagePath = null;
    }
    
    public boolean hasImage() {
        return this.imagePath != null;
    }
    
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
    
    public final boolean isPublished() {
        return this.published;
    }
    
    public void flag(Flag flag) {
        this.flag = flag;
    }
    
    public void unflag() {
        this.flag = null;
    }
    
    
}
