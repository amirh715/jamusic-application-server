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
public class GetPlaylistsOfPlayerRequestDTO extends DTOWithAuthClaims {
    
    public GetPlaylistsOfPlayerRequestDTO(
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
    }
    
}
