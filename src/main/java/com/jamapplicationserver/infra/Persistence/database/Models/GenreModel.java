/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.io.Serializable;


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
public class GenreModel implements Serializable {
    
    public GenreModel() {
        
    }
    
    @Id
    @Column(name="id")
    private UUID id;
    
    @Column(name="title")
    private String title;
    
    @Column(name="title_in_persian", nullable=false)
    private String titleInPersian;
    
    // parent genre
    @ManyToOne
    @JoinColumn(name="parent_genre_id", nullable=true)
    private GenreModel parentGenre;
    
    @ManyToOne(optional=true)
    private UserModel updater;
    
    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false)
    private LocalDateTime lastModifiedAt;
    
    public UUID getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getTitleInPersian() {
        return this.titleInPersian;
    }
    
    public GenreModel getParentGenre() {
        return this.parentGenre;
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
    
    public void setId(UUID id) {
        this.id = id;
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
    
    public void setUpdater(UserModel updater) {
        this.updater = updater;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
    
    
}
