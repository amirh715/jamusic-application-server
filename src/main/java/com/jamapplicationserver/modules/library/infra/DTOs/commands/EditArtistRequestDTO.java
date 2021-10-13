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
public class EditArtistRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    public final String title;
    public final String description;
    public final Set<String> tags;
    public final Set<String> genreIds;
    public final String flagNote;
    public final String instagramId;
    public final InputStream image;
    public final Boolean removeImage;
    
    public final Set<String> bandMemberIds;
        
    public EditArtistRequestDTO(
            String id,
            String title,
            String description,
            String tags,
            String genreIds,
            String flagNote,
            String instagramId,
            InputStream image,
            String removeImage,
            String bandMemberIds,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        
        super(updaterId, updaterRole);
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.genreIds = serializer.deserialize(genreIds, new TypeToken<Set<String>>(){});
        this.flagNote = flagNote;
        this.instagramId = instagramId;
        this.image = image;
        this.removeImage = Boolean.parseBoolean(removeImage);
        this.bandMemberIds = serializer.deserialize(bandMemberIds, new TypeToken<Set<String>>(){});
    }
    
}
