/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.events;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.core.Artist;

/**
 *
 * @author dada
 */
public class ArtistRemoved extends DomainEvent {
    
    public final Artist artist;
    
    public ArtistRemoved(Artist artist) {
        super(artist.id);
        this.artist = artist;
    }
    
}
