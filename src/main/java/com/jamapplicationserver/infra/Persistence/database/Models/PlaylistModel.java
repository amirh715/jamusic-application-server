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
@Table(name="playlists", schema="jamschema")
@Audited
public class PlaylistModel extends EntityModel {
    
    @Column(name="title", nullable=false)
    private String title;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Column(name="last_modified_at", nullable=false)
    private LocalDateTime lastModifiedAt;
    
    @ManyToMany
    @JoinTable(
            name="playlist_tracks",
            joinColumns = {
                @JoinColumn(name="playlist_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name="track_id")
            }
    )
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Set<TrackModel> tracks = new HashSet<>();
    
    @ManyToOne()
    @JoinColumn(name="player_id")
    private UserModel player;
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
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
    
    public Set<TrackModel> getTracks() {
        return this.tracks;
    }
    
    public void addTrack(TrackModel track) {
        this.tracks.add(track);
    }
    
    public void removeTrack(TrackModel track) {
        this.tracks.remove(track);
    }
    
    public void replaceTracks(Set<TrackModel> newTracks) {
        this.tracks.removeAll(tracks);
        newTracks
                .forEach(newTrack -> tracks.add(newTrack));
    }
    
    private void setTrack(Set<TrackModel> tracks) {
        this.tracks = tracks;
    }
    
    public UserModel getPlayer() {
        return this.player;
    }
    
    public void setPlayer(UserModel player) {
        this.player = player;
    }
    
}
