/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import javax.persistence.*;
import org.hibernate.envers.*;

/**
 *
 * @author amirhossein
 */
@Entity
@DiscriminatorValue("A")
@Audited
public class AlbumModel extends ArtworkModel {
    
    @OneToMany(mappedBy="album", cascade=CascadeType.ALL)
    private Set<TrackModel> tracks = new HashSet<>();
    
    public AlbumModel() {
        super();
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
