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
@Table(name="playlists", schema="jamschema")
public class PlaylistModel implements Serializable {
    
    @Id
    @Column(name="id")
    private UUID id;
    
    @Column(name="title", nullable=false)
    private String title;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false)
    private LocalDateTime lastModifiedAt;
    
    @ManyToMany()
    @JoinTable(
            name="playlist_tracks",
            joinColumns = {
                @JoinColumn(name="playlist_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name="track_id")
            }
    )
    private Set<TrackModel> tracks;
    
    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserModel user;
    
    // userId
    
    // updatedBy
    
    
}
