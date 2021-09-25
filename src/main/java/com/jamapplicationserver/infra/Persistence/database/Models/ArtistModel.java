/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dada
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name="Artist_FetchSingleTracks",
            query="SELECT tracks FROM ArtistModel artist, TrackModel tracks WHERE tracks.album = NULL"
    )
})
public abstract class ArtistModel extends LibraryEntityModel implements Serializable {
    
    @Column(name="instagram_id")
    private String instagramId;
    
    @OneToMany(mappedBy="artist", fetch=FetchType.LAZY)
    private Set<AlbumModel> albums = new HashSet<AlbumModel>();
    
    @OneToMany(mappedBy="artist", fetch=FetchType.LAZY)
    private Set<TrackModel> tracks = new HashSet<TrackModel>();
    
    protected ArtistModel() {
        super();
        this.albums = new HashSet<>();
        this.tracks = new HashSet<>();
    }
    
    public String getInstagramId() {
        return this.instagramId;
    }
    
    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }
    
    public Set<AlbumModel> getAlbums() {
        return this.albums;
    }
    
    protected void setAlbums(Set<AlbumModel> albums) {
        this.albums = albums;
    }
    
    public void addAlbum(AlbumModel album) {
        this.albums.add(album);
    }
    
    public void removeAlbum(AlbumModel album) {
        this.albums.remove(album);
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
