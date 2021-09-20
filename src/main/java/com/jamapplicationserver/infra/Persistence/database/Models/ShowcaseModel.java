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
@Table(name="showcases", schema="jamschema")
public class ShowcaseModel implements Serializable {
    
    @Id
    @Column(name="id")
    private UUID id;
    
    @Column(name="index")
    private int index;
    
    @Column(name="title", unique=true, nullable=true)
    private String title;
    
    @Column(name="description", nullable=true)
    private String description;
    
    @Column(name="image_path", unique=true, nullable=true)
    private String imagePath;
    
    @Column(name="route", nullable=true)
    private String route;
    
    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false)
    private LocalDateTime lastModifiedAt;
    
    @Column(name="interaction_count", nullable=false)
    private long interactionCount;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="creator_id")
    private UserModel creator;
    
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(int index) {
        this.index = index;
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
    
    public String getImagePath() {
        return this.imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public String getRoute() {
        return this.route;
    }
    
    public void setRoute(String route) {
        this.route = route;
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
    
    public long getInteractionCount() {
        return this.interactionCount;
    }
    
    public void setInteractionCount(long interactionCount) {
        this.interactionCount = interactionCount;
    }
    
    public void incrementInteractionCount() {
        this.interactionCount++;
    }
    
    public UserModel getCreator() {
        return this.creator;
    }
    
    public void setCreator(UserModel creator) {
        this.creator = creator;
    }
    
}
