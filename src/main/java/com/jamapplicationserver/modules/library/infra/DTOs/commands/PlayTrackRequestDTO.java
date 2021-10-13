/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class PlayTrackRequestDTO extends DTOWithAuthClaims {
    
    public String trackId;
    public String playedAt;
    
    public PlayTrackRequestDTO(
            UniqueEntityId playerId,
            UserRole playerRole,
            String trackId,
            String playedAt
    ) {
        super(playerId, playerRole);
        this.trackId = trackId;
        this.playedAt = playedAt;
    }
    
}
