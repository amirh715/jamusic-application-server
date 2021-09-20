/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
public class PlayedId implements Serializable {

    public UUID playedTrack;
    
    public UUID player;

    public LocalDateTime playedAt;
    
    PlayedId() {
        
    }
    
    PlayedId(
            UUID playedTrack,
            UUID player,
            LocalDateTime playedAt
    ) {
        this.playedTrack = playedTrack;
        this.player = player;
        this.playedAt = playedAt;
    }

    public UUID getPlayedTrackId() {
        return this.playedTrack;
    }

    public void setPlayedTrackId(UUID playedTrack) {
        this.playedTrack = playedTrack;
    }

    public UUID getPlayerId() {
        return this.player;
    }

    public void setPlayerId(UUID player) {
        this.player = player;
    }
    
    public LocalDateTime getPlayedAt() {
        return this.playedAt;
    }
    
    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(player, playedTrack, playedAt);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof PlayedId)) return false;
        final PlayedId pk = (PlayedId) obj;
        return
                pk.player.equals(this.player) &&
                pk.playedTrack.equals(this.playedTrack) &&
                pk.playedAt.equals(this.playedAt);
    }
     
}
