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
import org.hibernate.envers.*;
import com.jamapplicationserver.infra.Persistence.database.EntityListeners.*;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="library_entities", schema="jamschema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="entity_type", discriminatorType=DiscriminatorType.STRING)
@org.hibernate.annotations.DiscriminatorOptions(force=true)
@EntityListeners(DomainEventDispatcher.class)
@Audited
public abstract class LibraryEntityModel extends EntityModel {
    
    public LibraryEntityModel() {
        
    }

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
    private double rate;
    
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
    protected Set<GenreModel> genres = new HashSet<GenreModel>();
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY) // false for production
    private UserModel creator;
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY) // false for production
    private UserModel updater;
    
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
        if(this.tags == null || this.tags.isEmpty()) return new HashSet<>();
        final String separator = "#";
        final List<String> tagsList = Arrays.asList(this.tags.split(separator));
        return tagsList.stream().collect(Collectors.toSet());
    }
    
    public void setTags(Set<String> tags) {
        if(tags == null || tags.isEmpty()) {
            this.tags = "";
            return;
        }
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
    
    public void setRate(double rate) {
        this.rate = rate;
    }
    
    public Set<GenreModel> getGenres() {
        return this.genres;
    }
    
    public void replaceGenres(Set<GenreModel> genres) {
        this.genres.addAll(genres);
        this.genres.removeIf(g -> !genres.contains(g));
    }
    
    public void addGenre(GenreModel genreToAdd) {

        if(this.genres.isEmpty()) {
            this.genres.add(genreToAdd);
        } else {
            this.genres.forEach(genre -> {
                if(genre.isSubGenreOf(genreToAdd) || genre.equals(genreToAdd))
                    return;
                if(genreToAdd.isSubGenreOf(genre))
                    this.genres.remove(genre);
                this.genres.add(genreToAdd);
            });
        }
        
    }
    
    public void removeGenre(GenreModel genre) {
        this.genres.remove(genre);
    }
    
    protected void setGenres(Set<GenreModel> genres) {
        this.genres = genres;
    }
    
    public UserModel getCreator() {
        return this.creator;
    }
    
    public void setCreator(UserModel creator) {
        this.creator = creator;
    }
    
    public UserModel getUpdater() {
        return this.updater;
    }
    
    public void setUpdater(UserModel updater) {
        this.updater = updater;
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof LibraryEntityModel)) return false;
        final LibraryEntityModel le = (LibraryEntityModel) obj;
        return this.id.equals(le.id);
    }
    
}
