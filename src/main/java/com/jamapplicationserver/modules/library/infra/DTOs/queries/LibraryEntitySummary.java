/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import java.util.*;
import java.util.stream.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class LibraryEntitySummary implements IQueryResponseDTO {
    
    public final String id;
    public final String title;
    public final String published;
    public final long dailyPlayedCount;
    public final double rate;
    public final Set<String> genres;
    
    private LibraryEntitySummary(
            String id,
            String title,
            String published,
            long dailyPlayedCount,
            double rate,
            Set<String> genres
    ) {
        this.id = id;
        this.title = title;
        this.published = published;
        this.dailyPlayedCount = dailyPlayedCount;
        this.rate = rate;
        this.genres = genres;
    }
    
    /**
     * Factory method for creating LibraryEntitySummary instances
     * @param entity
     * @return LibraryEntitySummary
     */
    public static LibraryEntitySummary create(LibraryEntityModel entity) {
        
        return new LibraryEntitySummary(
                entity.getId().toString(),
                entity.getTitle(),
                entity.isPublished() ? "" : "",
                // dailyPlayedCount
                0,
                entity.getRate(),
                entity.getGenres()
                .stream()
                    .map(genre -> genre.getTitleInPersian())
                    .collect(Collectors.toSet())
        );
    }
    
    @Override
    public IQueryResponseDTO filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER: break;
            case SUBSCRIBER:
                
                break;
            default:
                return null;
        }
        return this;
    }
    
}
