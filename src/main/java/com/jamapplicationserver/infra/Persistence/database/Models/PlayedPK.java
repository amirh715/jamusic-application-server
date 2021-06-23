/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
public class PlayedPK implements Serializable {
    
    @Column(name="played_track_id")
    private UUID playedTrackID;
    
    @Column(name="player_id")
    private UUID playerID;
    
    @Column(name="played_at")
    private LocalDateTime playedAt;
    
    PlayedPK() {
        
    }
    
}
