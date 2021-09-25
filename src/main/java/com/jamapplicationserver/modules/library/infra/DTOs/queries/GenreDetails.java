/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.domain.DateTime;
import com.jamapplicationserver.core.domain.IDTO;
import java.util.stream.Collectors;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class GenreDetails implements IDTO {
    
    public final String id;
    public final String title;
    public final String titleInPersian;
    public final String createdAt;
    public final String lastModifiedAt;
    public final String creatorName;
    public final String creatorId;
    public final String updaterName;
    public final String updaterId;
    public final Set<GenreDetails> subGenres;
    
    private GenreDetails(
            String id,
            String title,
            String titleInPersian,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            String creatorName,
            String creatorId,
            String updaterName,
            String updaterId,
            Set<GenreDetails> subGenres
    ) {
        this.id = id;
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.createdAt = DateTime.createWithoutValidation(createdAt).toJalali();
        this.lastModifiedAt = DateTime.createWithoutValidation(lastModifiedAt).toJalali();
        this.creatorName = creatorName;
        this.creatorId = creatorId;
        this.updaterName = updaterName;
        this.updaterId = updaterId;
        this.subGenres = subGenres;
    }

    public static GenreDetails create(GenreModel genre) {
        return new GenreDetails(
                genre.getId().toString(),
                genre.getTitle(),
                genre.getTitleInPersian(),
                genre.getCreatedAt(),
                genre.getLastModifiedAt(),
                genre.getCreator().getName(),
                genre.getCreator().getId().toString(),
                genre.getUpdater().getName(),
                genre.getUpdater().getId().toString(),
                genre.getSubGenres()
                    .stream()
                    .map(subGenre -> create(subGenre))
                    .collect(Collectors.toSet())
        );
    }
    
}
