/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import java.util.*;
import java.io.InputStream;
import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.GenreDTO;

/**
 *
 * @author dada
 */
public class EditLibraryEntityRequestDTO implements IDTO {
    
    public final String type;
    public final String id;
    public final String title;
    public final String description;
    public final Set<String> tags;
    public final Set<GenreDTO> genres;
    public final String flagNote;
    public final String instagramId;
    
    public final InputStream image;
    
    public EditLibraryEntityRequestDTO(
            String type,
            String id,
            String title,
            String description,
            String tags,
            String genres,
            String flagNote,
            String instagramId,
            InputStream image
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.type = type;
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.genres = serializer.deserialize(genres, new TypeToken<Set<GenreDTO>>(){});
        this.flagNote = flagNote;
        this.instagramId = instagramId;
        this.image = image;
    }
    
}
