/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.EntityListeners;

import java.util.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class ArtworksInheritedTagsSetter {
    
    public ArtworksInheritedTagsSetter() {
        
    }
    
    @PrePersist
    @PreUpdate
    public void setInheritedTagsForArtworks(LibraryEntityModel entity) {

        if(entity instanceof ArtworkModel) {
            
            final ArtworkModel artwork = (ArtworkModel) entity;
            final Set<String> inheritedTags = new HashSet<>();
            
            final ArtistModel artist = artwork.getArtist();
            
            if(artist != null && artist.getTags() != null) {
                
                final String artistTitle = artwork.getArtist().getTitle();
                
                inheritedTags.addAll(artist.getTags());
                inheritedTags.add(artistTitle);
            }
            
            if(entity instanceof TrackModel) {
                
                final TrackModel track = (TrackModel) entity;
                
                if(track.isAlbumTrack()) {
                    
                    final String albumTitle = track.getAlbum().getTitle();
                    
                    inheritedTags.addAll(track.getAlbum().getTags());
                    inheritedTags.add(albumTitle);
                }
                
            }
            
            artwork.setInheritedTags(inheritedTags);
            
        }
        
    }
    
}
