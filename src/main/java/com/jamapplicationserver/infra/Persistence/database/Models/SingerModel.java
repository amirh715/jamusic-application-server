/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
@Entity
@DiscriminatorValue("S")
public class SingerModel extends LibraryEntityModel {
    
    public SingerModel() {
        
    }
    
    @Column(name="instagram_id", unique=true)
    private String instagramId;
    
    @ManyToMany(mappedBy="singer")
    private Set<TrackModel> tracks;
    
    @ManyToMany(mappedBy="singer")
    private Set<AlbumModel> albums;
    
    @ManyToMany(mappedBy="members")
    private Set<BandModel> bands;
    
    public String getInstagramId() {
        return this.instagramId;
    }
    
    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public Set<TrackModel> getTracks() {
        return this.tracks;
    }
    
    public void setTracks(Set<TrackModel> tracks) {
        this.tracks = tracks;
    }
    
    public Set<AlbumModel> getAlbums() {
        return this.albums;
    }
    
    public void setAlbums(Set<AlbumModel> albums) {
        this.albums = albums;
    }
    
    public Set<BandModel> getBands() {
        return this.bands;
    }
    
    public void setBands(Set<BandModel> bands) {
        this.bands = bands;
    }
    
}
