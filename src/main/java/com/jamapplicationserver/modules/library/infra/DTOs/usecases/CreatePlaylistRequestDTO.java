/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import java.util.*;
import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.core.infra.*;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author dada
 */
public class CreatePlaylistRequestDTO implements IDTO {
    
    public final String title;
    public final String playerId;
    public final Set<String> trackIds;
    
    public CreatePlaylistRequestDTO(
            String title,
            String playerId,
            String trackIds
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.title = title;
        this.playerId = playerId;
        this.trackIds = serializer.deserialize(trackIds, new TypeToken<Set<String>>(){});
        
    }
    
}
