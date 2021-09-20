/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author amirhossein
 */
@Entity
@DiscriminatorValue("A")
//@AssociationOverride(name="artist", joinColumns=@JoinColumn(name="album_artist_id"))
public class AlbumModel extends ArtworkModel implements Serializable {
    
    @OneToMany(mappedBy="album", cascade=CascadeType.ALL)
    private Set<TrackModel> tracks;
    
    public AlbumModel() {
        super();
        this.tracks = new HashSet<>();
    }
    
    public Set<TrackModel> getTracks() {
        return this.tracks;
    }
    
    protected void setTracks(Set<TrackModel> tracks) {
        this.tracks = tracks;
    }
    
    public void addTrack(TrackModel track) {
        this.tracks.add(track);
    }
    
    public void removeTrack(TrackModel track) {
        this.tracks.remove(track);
    }
    
}
