/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import java.util.*;
import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.infra.DTOWithAuthClaims;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class EditPlaylistRequestDTO extends DTOWithAuthClaims {
    
    public String id;
    public String title;
    public Set<String> trackIds;
    
    public EditPlaylistRequestDTO(
            String id,
            String title,
            String trackIds,
            UniqueEntityId subjectId,
            UserRole subjectRole
    ) {
        super(subjectId, subjectRole);
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.id = id;
        this.title = title;
        this.trackIds = serializer.deserialize(trackIds, new TypeToken<Set<String>>(){});
        
    }
    
}
