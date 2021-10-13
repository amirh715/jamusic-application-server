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
public class CreateGenreRequestDTO extends DTOWithAuthClaims {
    
    public final String title;
    public final String titleInPersian;
    public final String parentGenreId;
    
    public CreateGenreRequestDTO(
            String title,
            String titleInPersian,
            String parentGenreId,
            UniqueEntityId creatorId,
            UserRole creatorRole
    ) {
        
        super(creatorId, creatorRole);
        
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenreId = parentGenreId;
    }
    
}
