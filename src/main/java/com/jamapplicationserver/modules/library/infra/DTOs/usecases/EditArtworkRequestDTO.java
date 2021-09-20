/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import java.util.*;
import java.io.InputStream;
import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class EditArtworkRequestDTO implements IDTO {
    
    public final String id;
    public final String title;
    public final String description;
    public final Set<String> genreIds;
    public final Set<String> tags;
    public final String flagNote;
    public final String recordLabel;
    public final String producer;
    public final String releaseYear;
    public final String lyrics;
    public final InputStream image;
    public final Boolean removeImage;
    
    public String updaterId;
    public String updaterRole;
    
    public EditArtworkRequestDTO(
            String id,
            String title,
            String description,
            String genreIds,
            String tags,
            String flagNote,
            String recordLabel,
            String producer,
            String releaseYear,
            String lyrics,
            InputStream image,
            String removeImage,
            String updaterId,
            String updaterRole
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.id = id;
        this.title = title;
        this.description = description;
        this.genreIds = serializer.deserialize(genreIds, new TypeToken<Set<String>>(){});
        this.tags = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.flagNote = flagNote;
        this.recordLabel = recordLabel;
        this.producer = producer;
        this.releaseYear = releaseYear;
        this.lyrics = lyrics;
        this.image = image;
        this.removeImage = Boolean.parseBoolean(removeImage);
        this.updaterId = updaterId;
        this.updaterRole = updaterRole;
        
    }
    
}
