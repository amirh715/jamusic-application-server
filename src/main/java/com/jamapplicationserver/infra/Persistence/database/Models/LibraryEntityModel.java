/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import com.jamapplicationserver.infra.Persistence.database.EntityListeners.ArtworksInheritedTagsSetter;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.persistence.*;
import org.hibernate.envers.*;
import com.jamapplicationserver.infra.Persistence.database.EntityListeners.DomainEventDispatcher;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="library_entities", schema="jamschema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="entity_type", discriminatorType=DiscriminatorType.STRING)
@org.hibernate.annotations.DiscriminatorOptions(force=true)
@EntityListeners({DomainEventDispatcher.class, ArtworksInheritedTagsSetter.class})
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
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="creator_id", nullable=false)
    private UserModel creator;
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="updater_id", nullable=false)
    private UserModel updater;
    
    @OneToMany(orphanRemoval=true, mappedBy="reportedEntity")
    private Set<ReportModel> reports = new HashSet<>();
    
    @Column(name="artworks_inherited_tags")
    private String artworksInheritedTags;
    
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
        if(description == null) return;
        if(description.isBlank()) this.description = null;
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
            this.tags = null;
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
        if(flagNote == null) return;
        if(flagNote.isBlank()) this.flagNote = null;
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
        this.genres.removeIf(g -> !genres.contains(g));
        genres.forEach(g -> addGenre(g));
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
    
    public Set<ReportModel> getReports() {
        return this.reports;
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
    
    public Set<String> getArtworksInheritedTags() {
        if(this.artworksInheritedTags == null || this.artworksInheritedTags.isEmpty())
            return new HashSet<>();
        final String separator = "#";
        final List<String> tagsList = Arrays.asList(this.artworksInheritedTags.split(separator));
        return tagsList.stream().collect(Collectors.toSet());
    }
    
    public void setArtworksInheritedTags(Set<String> tags) {
        if(!(this instanceof ArtworkModel)) return;
        if(tags == null || tags.isEmpty()) {
            this.artworksInheritedTags = null;
            return;
        }
        final String separator = "#";
        this.artworksInheritedTags =
                tags.stream()
                .map(tag -> {
                    return tag
                            .trim()
                            .replace(" ", "_")
                            .concat(separator);
                })
                .collect(Collectors.joining());
    }
    
    @Override
    public int hashCode() {
        System.out.println("LibraryEntityModel::hashCode");
        return this.getId().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        System.out.println("LibraryEntityModel::equals");
        if(this == obj) return true;
        if(!(obj instanceof LibraryEntityModel)) return false;
        final LibraryEntityModel le = (LibraryEntityModel) obj;
        return this.getId().equals(le.getId());
    }
    
}
