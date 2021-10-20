/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.*;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.envers.*;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="played_tracks", schema="jamschema")
@IdClass(PlayedId.class)
@Audited
public class PlayedModel implements Serializable {
    
    @Id
    @ManyToOne(optional=false)
    @JoinColumn(name="played_track_id", referencedColumnName="id", updatable=false)
    private TrackModel playedTrack;
    
    @Id
    @ManyToOne(optional=false)
    @JoinColumn(name="player_id", referencedColumnName="id", updatable=false)
    private UserModel player;
    
    @Id
    @Column(name="played_at", updatable=false)
    private LocalDateTime playedAt;
    
    public PlayedModel() {
        
    }
    
    public PlayedModel(
            TrackModel playedTrack,
            UserModel player,
            LocalDateTime playedAt
    ) {
        this.player = player;
        this.playedTrack = playedTrack;
        this.playedAt = playedAt;
    }
    
    public void setId(PlayedId id) {
        playedTrack.setId(id.playedTrack);
        player.setId(id.player);
        playedAt = id.playedAt;
    }
    
    public UserModel getPlayer() {
        return this.player;
    }
    
    public TrackModel getPlayedTrack() {
        return this.playedTrack;
    }
    
    public LocalDateTime getPlayedAt() {
        return this.playedAt;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(player, playedTrack, playedAt);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof PlayedModel)) return false;
        final PlayedModel pm = (PlayedModel) obj;
        return
                pm.getPlayer().equals(this.player) &&
                pm.getPlayedTrack().equals(this.playedTrack) &&
                pm.getPlayedAt().equals(this.playedAt);
    }
    
}
