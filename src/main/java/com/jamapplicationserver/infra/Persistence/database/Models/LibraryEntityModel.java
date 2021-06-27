/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="library_entities", schema="jamschema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="entity_type", discriminatorType = DiscriminatorType.STRING)
public class LibraryEntityModel {
    
    public LibraryEntityModel() {
        
    }
    
    @Id
    @Column(name="id")
    private UUID id;
    
    @Column(name="title", unique=false, nullable=false, columnDefinition="")
    private String title;
    
    @Column(name="description", nullable=true, columnDefinition="")
    private String description;
    
    @Column(name="published", nullable=false, columnDefinition="")
    private boolean published;
    
    @Column(name="image_path", nullable=true, columnDefinition="")
    private String imagePath;
    
    @Column(name="tags", nullable=true, columnDefinition="")
    private String tags;
    
    @Column(name="flag_note", nullable=true, columnDefinition="")
    private String flagNote;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false, columnDefinition="")
    private LocalDateTime lastModifiedAt;
    
    @Column(name="total_played_count", nullable=false, columnDefinition="")
    private long totalPlayedCount;
    
    @Column(name="duration", nullable=false, columnDefinition="")
    private long duration;
    
    @Column(name="rate", nullable=false, columnDefinition="")
    private float rate;
    
    @ManyToMany
    @JoinTable(
            name="entity_genres",
            joinColumns = {
                @JoinColumn(name="entity_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name="genre_id")
            }
    )
    private Set<GenreModel> genres = new HashSet<GenreModel>();
    
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isPublished() {
        return this.published;
    }
    
    public void setPublished(boolean published) {
        this.published = published;
    }
    
    public String getImagePath() {
        return this.imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public Set<String> getTags() {
        final String separator = "#";
        final List<String> tagsList = Arrays.asList(tags.split(separator));
        return tagsList.stream().collect(Collectors.toSet());
    }
    
    public void setTags(Set<String> tags) {
        if(tags == null) return;
        final String separator = "#";
        this.tags = tags.stream()
                .map(tag -> {
                    return tag
                            .trim()
                            .replace(" ", "_")
                            .concat(separator);
                })
                .collect(Collectors.joining());
    }
    
    public String getFlagNote() {
        return this.flagNote;
    }
    
    public void setFlagNote(String flagNote) {
        this.flagNote = flagNote;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
    
    public long getTotalPlayedCount() {
        return this.totalPlayedCount;
    }
    
    public void setTotalPlayedCount(long totalPlayedCount) {
        this.totalPlayedCount = totalPlayedCount;
    }
    
    public long getDuration() {
        return this.duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public double getRate() {
        return this.rate;
    }
    
    public void setRate(float rate) {
        this.rate = rate;
    }
    
    public Set<GenreModel> getGenres() {
        return this.genres;
    }
    
    public void setGenres(Set<GenreModel> genres) {
        this.genres = genres;
    }
    
}
