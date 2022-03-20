/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.subscribers;

import java.util.*;
import java.util.stream.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.events.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author dada
 */
public class AfterArtistPublished implements IDomainEventHandler<ArtistPublished> {
    
    @Override
    public Class subscribedToEventType() {
        return ArtistPublished.class;
    }
    
    @Override
    public void handleEvent(ArtistPublished event) throws Exception {
        
        // publish artist's artworks as well
        if(event.cascadeToArtworks) {
            
            try {
                
                final UniqueEntityId updaterId = event.artist.getUpdaterId();
            
                final ILibraryEntityRepository repository =
                    LibraryEntityRepository.getInstance();

                final LibraryEntityFilters artistsArtworksFilter =
                        new LibraryEntityFilters();
                artistsArtworksFilter.artistId = event.artist.getId();
                
                final Set<Artwork> artworks =
                        repository.fetchByFilters(artistsArtworksFilter)
                        .includeUnpublished(UserRole.ADMIN)
                        .getResults();
                
                final Set<Album> albums =
                        artworks.stream()
                        .filter(artwork -> artwork instanceof Album)
                        .map(album -> (Album) album)
                        .collect(Collectors.toSet());
                for(Album album : albums) {
                    album.publish(updaterId);
                    repository.save(album);
                }
                
                final Set<Track> singleTracks =
                        artworks.stream()
                        .filter(artwork -> artwork instanceof Track)
                        .map(track -> (Track) track)
                        .filter(track -> track.isSingleTrack())
                        .collect(Collectors.toSet());
                for(Track singleTrack : singleTracks) {
                    singleTrack.publish(updaterId);
                    repository.save(singleTrack);
                }

            } catch(Exception e) {
                LogService.getInstance().error(e);
                throw e;
            }
            
        }
        
    }
    
    
}
