/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.io.Serializable;


/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="showcases", schema="jamschema")
public class ShowCaseModel implements Serializable {
    
    @Id
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
    private int interactionCount;
    
    @ManyToOne(optional=false)
    private UserModel createdBy;
    
}
