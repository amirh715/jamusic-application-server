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
import com.jamapplicationserver.modules.library.infra.DTOs.entities.*;

/**
 *
 * @author dada
 */
public class CreateLibraryEntityRequestDTO implements IDTO {
    
    // library entity props
    public final String type;
    public final String title;
    public final String description;
    public final Set<GenreDTO> genres;
    public final Set<String> tags;
    public final String flagNote;
    public final InputStream image;
    
    // track & album prop
    public final String artistId;
    
    // track props
    public final InputStream audio;
    public final String lyrics;

    // artist props
    public final String instagramId;
    
    // album props
    public final ArrayList<AlbumTrackDTO> tracks;
    
    public CreateLibraryEntityRequestDTO(
            String type,
            String title,
            String description,
            String genres,
            String tags,
            String flagNote,
            String artistId,
            String instagramId,
            String lyrics,
            String tracks,
            InputStream image,
            InputStream audio
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.type = type;
        this.title = title;
        this.description = description;
        this.genres = serializer.deserialize(genres, new TypeToken<List<GenreDTO>>(){});
        this.tags = serializer.deserialize(tags, new TypeToken<List<String>>(){});
        this.flagNote = flagNote;
        this.artistId = artistId;
        this.instagramId = instagramId;
        this.image = image;
        this.audio = audio;
        this.lyrics = lyrics;
        this.tracks = serializer.deserialize(tracks, new TypeToken<List<AlbumTrackDTO>>(){});
    }
    
    
}
