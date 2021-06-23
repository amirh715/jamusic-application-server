/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class PlayTrackRequestDTO implements IDTO {
    
    public String playerId;
    public String trackId;
    public String playedAt;
    
    public PlayTrackRequestDTO(
            String playerId,
            String trackId,
            String playedAt
    ) {
        this.playerId = playerId;
        this.trackId = trackId;
        this.playedAt = playedAt;
    }
    
}
