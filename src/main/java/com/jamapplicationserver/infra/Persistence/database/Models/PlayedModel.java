/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;
import java.util.Objects;
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
    
    @ManyToOne(optional=false)
    @JoinColumn(name="played_track_id", referencedColumnName="id", updatable=false, nullable=false)
    @Id
    private TrackModel playedTrack;

    @ManyToOne(optional=false)
    @JoinColumn(name="player_id", referencedColumnName="id", updatable=false, nullable=false)
    @Id
    private UserModel player;

    @Id
    @Column(name="played_at", updatable=false)
    private LocalDateTime playedAt;
    
    public PlayedModel() {
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
        return Objects.hash(player, playedTrack, playedAt);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof PlayedModel))
            return false;
        final PlayedModel p = (PlayedModel) obj;
        return Objects.equals(p.player.id, player.id) &&
                Objects.equals(p.playedTrack.id, playedTrack.id) &&
                Objects.equals(p.playedAt, playedAt);
    }

}
