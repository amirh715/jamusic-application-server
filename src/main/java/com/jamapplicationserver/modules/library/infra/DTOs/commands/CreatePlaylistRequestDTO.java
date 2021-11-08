/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author dada
 */
public class CreatePlaylistRequestDTO extends DTOWithAuthClaims {
    
    public final String title;
    public final Set<String> trackIds;
    
    public CreatePlaylistRequestDTO(
            String title,
            String trackIds,
            UniqueEntityId creatorId,
            UserRole creatorRole
    ) {
        
        super(creatorId, creatorRole);
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.title = title;
        this.trackIds =
                trackIds != null ?
                serializer.deserialize(trackIds, new TypeToken<Set<String>>(){}) : new HashSet<>();
        
    }
    
}
