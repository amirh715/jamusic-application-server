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
public class EditGenreRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    public final String title;
    public final String titleInPersian;
    
    public EditGenreRequestDTO(
            String id,
            String title,
            String titleInPersian,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        
        super(updaterId, updaterRole);
        
        this.id = id;
        this.title = title;
        this.titleInPersian = titleInPersian;
    }
    
}
