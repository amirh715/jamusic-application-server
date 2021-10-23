/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.*;
import org.hibernate.envers.*;

/**
 *
 * @author dada
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name="Artist_FetchSingleTracks",
            query="SELECT tracks FROM ArtistModel artist, TrackModel tracks WHERE tracks.album = NULL AND tracks.artist.id = ?1"
    )
})
@Audited
public abstract class ArtistModel extends LibraryEntityModel {
    
    @Column(name="instagram_id")
    private String instagramId;
    
    @OneToMany(mappedBy="artist", fetch=FetchType.LAZY)
    @NotAudited
    private Set<AlbumModel> albums = new HashSet<AlbumModel>();
    
    @OneToMany(mappedBy="artist", fetch=FetchType.LAZY)
    @NotAudited
    private Set<TrackModel> tracks = new HashSet<TrackModel>();
    
    protected ArtistModel() {

    }
    
    public String getInstagramId() {
        return this.instagramId;
    }
    
    public void setInstagramId(String instagramId) {
        if(instagramId == null) return;
        if(instagramId.isBlank()) this.instagramId = null;
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

    public Set<TrackModel> getSingleTracks() {
        return this.tracks
                .stream()
                .filter(track -> track.getAlbum() == null)
                .collect(Collectors.toSet());
    }
    
    public void removeTrack(TrackModel track) {
        this.tracks.remove(track);
    }

}
