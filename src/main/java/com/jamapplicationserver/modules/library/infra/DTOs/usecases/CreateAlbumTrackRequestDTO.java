/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.infra.ISerializer;
import com.jamapplicationserver.core.infra.Serializer;
import java.io.InputStream;
import java.util.*;

/**
 *
 * @author dada
 */
public class CreateAlbumTrackRequestDTO {
    
    public final String title;
    public final String description;
    public final Set<String> tags;
    public final String flagNote;
    
    public InputStream audio;
    
    public final String lyrics;
    
    public final String fileName;
    
    public CreateAlbumTrackRequestDTO(
            String title,
            String description,
            String tags,
            String flagNote,
            String lyrics,
            InputStream audio,
            String fileName
    ) {
        final ISerializer serializer = Serializer.getInstance();
        
        this.title = title;
        this.description = description;
        this.tags = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.flagNote = flagNote;
        this.lyrics = lyrics;
        this.audio = audio;
        this.fileName = fileName;
    }
    
    
}
