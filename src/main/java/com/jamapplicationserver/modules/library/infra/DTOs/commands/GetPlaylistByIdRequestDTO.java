/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.DTOWithAuthClaims;

/**
 *
 * @author dada
 */
public class GetPlaylistByIdRequestDTO extends DTOWithAuthClaims {
    
    public String playlistId;
    
    public GetPlaylistByIdRequestDTO(
            String playlistId,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        this.playlistId = playlistId;
    }
    
}
