/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.events;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author dada
 */
public class TrackArchived extends DomainEvent {
    
    public final Track track;
    
    public TrackArchived(Track track) {
        super(track.id);
        this.track = track;
    }
    
}
