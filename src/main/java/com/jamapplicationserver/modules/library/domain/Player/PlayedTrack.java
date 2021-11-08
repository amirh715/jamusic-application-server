/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Player;

import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public class PlayedTrack extends ValueObject {
    
    private final UniqueEntityId playedTrackId;
    private final DateTime playedAt;
    
    public PlayedTrack(UniqueEntityId playedTrackId, DateTime playedAt) {
        this.playedTrackId = playedTrackId;
        this.playedAt = playedAt;
    }
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    public UniqueEntityId getPlayedTrackId() {
        return this.playedTrackId;
    }
    
    public DateTime getPlayedAt() {
        return this.playedAt;
    }
    
}
