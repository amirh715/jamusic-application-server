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
@DiscriminatorValue("A")
public class AlbumModel extends LibraryEntityModel {
    
    public AlbumModel() {
        
    }
    
    @ManyToOne
    @JoinColumn(name="singer_id")
    private SingerModel singer;
    
    @ManyToOne
    @JoinColumn(name="band_id")
    private BandModel band;
    
    @OneToMany
    @JoinColumn(name="album_id")
    private Set<TrackModel> tracks;
    
    public SingerModel getSinger() {
        return this.singer;
    }
    
    public BandModel getBand() {
        return this.band;
    }
    
    public Set<TrackModel> getTracks() {
        return this.tracks;
    }
    
    public void setTracks(Set<TrackModel> tracks) {
        this.tracks = tracks;
    }
    
    
}
