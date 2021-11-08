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
public class RemovePlaylistRequestDTO extends DTOWithAuthClaims {
    
    public String id;
    
    public RemovePlaylistRequestDTO(
            String id,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        this.id = id;
    }
    
}
