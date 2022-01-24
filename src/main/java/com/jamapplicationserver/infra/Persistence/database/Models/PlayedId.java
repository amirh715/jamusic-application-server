/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author dada
 */
public class PlayedId implements Serializable {

    private UserModel player;

    private TrackModel playedTrack;

    private LocalDateTime playedAt;
    
    public PlayedId() {
        
    }
    
    public UserModel getPlayer() {
        return this.player;
    }
    
    public void setPlayer(UserModel player) {
        this.player = player;
    }
    
    public TrackModel getPlayedTrack() {
        return this.playedTrack;
    }
    
    public void setPlayedTrack(TrackModel playedTrack) {
        this.playedTrack = playedTrack;
    }
    
    public LocalDateTime getPlayedAt() {
        return this.playedAt;
    }
    
    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt.truncatedTo(ChronoUnit.SECONDS);
    }
    
    @Override
    public int hashCode() {
        System.out.println("### PlayedId::hashCode ####");
        return Objects.hash(player, playedTrack, playedAt);
    }
    
    @Override
    public boolean equals(Object obj) {
        System.out.println("### PlayedId::equals ####");
        if(obj == this)
            return true;
        if(!(obj instanceof PlayedId))
            return false;
        final PlayedId p = (PlayedId) obj;
        return Objects.equals(p.player, player) &&
                Objects.equals(p.playedTrack, playedTrack) &&
                Objects.equals(p.playedAt, playedAt);
    }
    
}
