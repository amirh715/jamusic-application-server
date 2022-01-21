/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;

/**
 *
 * @author dada
 */
public class PlaylistDetails implements IQueryResponseDTO {
    
    public final UUID id;
    public final String title;
    public final Set<LibraryEntityIdAndTitle> tracks;
    public final LocalDateTime createdAt;
    public final LocalDateTime lastModifiedAt;
    
    public PlaylistDetails(
            UUID id,
            String title,
            Set<LibraryEntityIdAndTitle> tracks,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt
    ) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    
    @Override
    public PlaylistDetails filter(UserRole role) {
        return this;
    }
    
}
