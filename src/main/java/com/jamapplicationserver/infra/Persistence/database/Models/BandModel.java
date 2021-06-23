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
@DiscriminatorValue("B")
public class BandModel extends LibraryEntityModel {
    
    public BandModel() {
        
    }
    
    @Column(name="instagram_id", unique=true)
    private String instagramId;
    
    @ManyToMany
    @JoinTable(
        name="band_members",
        joinColumns = {
            @JoinColumn(name="singer_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name="band_id")
        }
    )
    private Set<SingerModel> members = new HashSet<SingerModel>();

    @OneToMany(mappedBy="band")
    private Set<AlbumModel> albums = new HashSet<AlbumModel>();
    
    @OneToMany(mappedBy="band")
    private Set<TrackModel> tracks = new HashSet<TrackModel>();
    
    public String getInstagramId() {
        return this.instagramId;
    }
    
    public Set<SingerModel> getMembers() {
        return this.members;
    }
    
    public void setMembers(Set<SingerModel> members) {
        this.members = members;
    }
    
    public Set<AlbumModel> getAlbums() {
        return this.albums;
    }
    
    public Set<TrackModel> getTracks() {
        return this.tracks;
    }
    
    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }
    
    public void setAlbums(Set<AlbumModel> albums) {
        this.albums = albums;
    }
    
    public void setTracks(Set<TrackModel> tracks) {
        this.tracks = tracks;
    }
    
}
