/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class GetTrackAudioByIdRequestDTO extends DTOWithAuthClaims {
    
    public String id;
    
    public GetTrackAudioByIdRequestDTO(
            String id,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        this.id = id;
    }
    
    
}
