/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Player;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.Result;

/**
 *
 * @author amirhossein
 */
public class PlayedTrack extends ValueObject {
    
    public final UniqueEntityID trackID;
    public final DateTime playedAt;
    
    private PlayedTrack(UniqueEntityID trackID, DateTime playedAt) {
        this.trackID = trackID;
        this.playedAt = playedAt;
    }
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    public static final Result<PlayedTrack> create(UniqueEntityID trackID, DateTime playedAt) {
        
        return Result.ok(new PlayedTrack(trackID, playedAt));
    }
    
}
