/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Player;

import java.util.List;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author amirhossein
 */
public class Player extends Entity {
    
    private final List<PlayedTrack> playedTracks;
    private final PlayerRole role;

    private Player(UniqueEntityID playerID, List<PlayedTrack> playedTracks, PlayerRole role) {
        super(playerID);
        this.playedTracks = playedTracks;
        this.role = role;
    }
    
    public static Result create(UniqueEntityID id, List<PlayedTrack> playedTracks, PlayerRole role) {
        
        
        
        return Result.ok(new Player(id, playedTracks, role));
    }
    
    public Result played(Track track, DateTime playedAt) {
        
        final Result<PlayedTrack> playedTrackOrError =
                PlayedTrack.create(track.id, playedAt);
        
        if(playedTrackOrError.isFailure) return playedTrackOrError;
        
        final PlayedTrack playedTrack = playedTrackOrError.getValue();
        
        this.playedTracks.add(playedTrack);
        
        track.playedBy(id, playedAt);
        
        return Result.ok();
    }

}
