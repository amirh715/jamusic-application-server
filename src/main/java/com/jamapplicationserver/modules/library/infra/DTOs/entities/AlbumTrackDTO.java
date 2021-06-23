/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.entities;

import java.util.Set;
import java.io.InputStream;
import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.core.infra.*;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author dada
 */
public class AlbumTrackDTO implements IDTO {
    
    public final InputStream audio;
    
    public final String title;
    public final String description;
    public final String flagNote;
    public final Set<String> tags;
    public final String lyrics;
    
    public AlbumTrackDTO(
            InputStream audio,
            String title,
            String description,
            String flagNote,
            String tags,
            String lyrics
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.audio = audio;
        this.title = title;
        this.description = description;
        this.flagNote = flagNote;
        this.tags = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.lyrics = lyrics;
    }
    
    
}
