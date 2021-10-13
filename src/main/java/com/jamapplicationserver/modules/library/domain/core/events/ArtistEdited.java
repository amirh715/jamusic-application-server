/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.events;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author dada
 */
public class ArtistEdited extends DomainEvent {
    
    public final Artist artist;
    
    public ArtistEdited(Artist artist) {
        super(artist.id);
        this.artist = artist;
    }
    
}
