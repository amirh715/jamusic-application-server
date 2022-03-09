/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import java.util.*;
import java.io.InputStream;
import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class CreateArtistRequestDTO extends DTOWithAuthClaims {
    
    public final String type;
    public final String title;
    public final String description;
    public final Set<String> genreIds;
    public final Set<String> tags;
    public final String flagNote;
    public final UniqueEntityId creatorId;
    public final InputStream image;
    public final String instagramId;
    
    public CreateArtistRequestDTO(
            String type,
            String title,
            String description,
            String genreIds,
            String tags,
            String flagNote,
            UniqueEntityId creatorId,
            UserRole creatorRole,
            String instagramId,
            InputStream image
    ) {
        
        super(creatorId, creatorRole);
        
        final ISerializer serializer = Serializer.getInstance();
                
        this.type = type;
        this.title = title;
        this.description = description;
        this.genreIds = serializer.deserialize(genreIds, new TypeToken<Set<String>>(){});
        this.tags = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.flagNote = flagNote;
        this.creatorId = creatorId;
        this.instagramId = instagramId;
        this.image = image;
        
    }
    
    
}
