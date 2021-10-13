/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.envers.*;


/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="genres", schema="jamschema",
        uniqueConstraints={
            @UniqueConstraint(columnNames={"title"}, name="title_unique_key"),
            @UniqueConstraint(columnNames={"title_in_persian"}, name="title_in_persian_unique_key")
        }
)
public class GenreModel extends EntityModel {
    
    public GenreModel() {
        
    }
    
    @Column(name="title", nullable=false)
    @Audited
    private String title;
    
    @Column(name="title_in_persian", nullable=false)
    @Audited
    private String titleInPersian;

    @ManyToOne
    @JoinColumn(name="parent_genre_id", nullable=true)
    private GenreModel parentGenre;
    
    @OneToMany(mappedBy="parentGenre", orphanRemoval=true)
    private Set<GenreModel> subGenres = new HashSet<>();
    
    @ManyToOne(optional=false) // false for production
    private UserModel creator;
    
    @ManyToOne(optional=false) // false for production
    @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
    private UserModel updater;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false)
    @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
    private LocalDateTime lastModifiedAt;
    
    public String getTitle() {
        return this.title;
    }
    
    public String getTitleInPersian() {
        return this.titleInPersian;
    }
    
    public GenreModel getParentGenre() {
        return this.parentGenre;
    }
    
    public Set<GenreModel> getSubGenres() {
        return this.subGenres;
    }
    
    public UserModel getCreator() {
        return this.creator;
    }
    
    public UserModel getUpdater() {
        return this.updater;
    }
    
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public LocalDateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setTitleInPersian(String titleInPersian) {
        this.titleInPersian = titleInPersian;
    }
    
    public void setParentGenre(GenreModel parentGenre) {
        this.parentGenre = parentGenre;
    }
    
    public void addSubGenre(GenreModel genre) {
        this.subGenres.add(genre);
    }
    
    public void removeSubGenre(GenreModel genre) {
        this.subGenres.remove(genre);
    }
    
    public boolean isSubGenreOf(GenreModel genre) {
        if(isRoot())
            return false;
        if(parentGenre.equals(genre))
            return true;
        return parentGenre.isSubGenreOf(genre);
    }
    
    public boolean isRoot() {
        return this.parentGenre == null;
    }
    
    public void setCreator(UserModel creator) {
        this.creator = creator;
    }
    
    public void setUpdater(UserModel updater) {
        this.updater = updater;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
    @Override
    public boolean equals(Object genre) {
        if(genre == this)
            return true;
        if(!(genre instanceof GenreModel))
            return false;
        GenreModel g = (GenreModel) genre;
        return g.id.equals(this.id);
    }
    
}
