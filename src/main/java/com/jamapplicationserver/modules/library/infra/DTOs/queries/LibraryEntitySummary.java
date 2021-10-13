/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import java.util.stream.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class LibraryEntitySummary {
    
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
    
}
