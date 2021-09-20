/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Player;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author amirhossein
 */
public class PlayedTrack extends ValueObject {
    
    public final Track track;
    public final DateTime playedAt;
    
    public PlayedTrack(Track track, DateTime playedAt) {
        this.track = track;
        this.playedAt = playedAt;
    }
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    public Track getTrack() {
        return this.track;
    }
    
    public DateTime getPlayedAt() {
        return this.playedAt;
    }
    
}
