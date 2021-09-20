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

/**
 *
 * @author dada
 */
public class CreateTrackRequestDTO implements IDTO {
    
    public final String title;
    public final String description;
    public final Set<String> genreIds;
    public final Set<String> tags;
    public final String flagNote;
    public final String creatorId;
    public final String recordLabel;
    public final String producer;
    public final Integer releaseYear;
    
    public final InputStream image;
    public InputStream audio;
    
    public final String artistId;
    public final String lyrics;
    
    public final String albumId;
    
    public CreateTrackRequestDTO(
            String title,
            String description,
            String genreIds,
            String tags,
            String flagNote,
            String creatorId,
            String lyrics,
            String artistId,
            String recordLabel,
            String producer,
            String releaseYear,
            InputStream image,
            InputStream audio,
            String albumId
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.title = title;
        this.description = description;
        this.genreIds = serializer.deserialize(genreIds, new TypeToken<Set<String>>(){});
        this.tags = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.flagNote = flagNote;
        this.creatorId = creatorId;
        this.lyrics = lyrics;
        this.artistId = artistId;
        this.recordLabel = recordLabel;
        this.producer = producer;
        this.releaseYear = Integer.parseInt(releaseYear);
        this.image = image;
        this.audio = audio;
        this.albumId = albumId;
        
    }

    
}
