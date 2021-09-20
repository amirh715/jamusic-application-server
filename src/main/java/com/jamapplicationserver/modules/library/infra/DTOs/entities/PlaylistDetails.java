/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.entities;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class PlaylistDetails implements IDTO {
    
    public final UniqueEntityId id;
    public final Title title;
    public final Set<TrackDetails> tracks;
    public final DateTime createdAt;
    public final DateTime lastModifiedAt;
    
    public PlaylistDetails(
            UniqueEntityId id,
            Title title,
            Set<TrackDetails> tracks,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    
    public static PlaylistDetails create(PlaylistModel playlist) {
        
        
        return null;
    }
    
}
