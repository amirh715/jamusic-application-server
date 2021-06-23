/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import javax.persistence.*;


/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="played", schema="jamschema")
public class PlayedModel implements Serializable {

    @EmbeddedId
    private PlayedPK id;
    
    @ManyToOne
    @MapsId("playerID")
    @JoinColumn(name="player_id")
    private UserModel player;
    
    @ManyToOne
    @MapsId("playedTrackID")
    @JoinColumn(name="played_track_id")
    private TrackModel playedTrack;
    
    public PlayedModel() {
        
    }
    
}
