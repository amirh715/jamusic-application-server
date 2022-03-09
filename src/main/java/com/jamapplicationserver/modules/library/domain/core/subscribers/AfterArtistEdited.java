/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.subscribers;

import java.util.*;
import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.events.*;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 */
public class AfterArtistEdited implements IDomainEventHandler<ArtistEdited> {
    
    @Override
    public void handleEvent(ArtistEdited event) throws Exception {
        
        try {
                        
            final ILibraryEntityRepository repository =
                    LibraryEntityRepository.getInstance();
            
            final LibraryEntityFilters artistsArtworksFilter = new LibraryEntityFilters();
            artistsArtworksFilter.artistId = event.aggregateId;
            
            final Set<Artwork> artworks =
                    repository.fetchByFilters(artistsArtworksFilter)
                    .includeUnpublished(UserRole.ADMIN)
                    .getResults();
            
            for(Artwork artwork : artworks) {
                repository.save(artwork);
            }
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public Class subscribedToEventType() {
        return ArtistEdited.class;
    }
    
}
