/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import java.util.stream.*;
import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;

/**
 *
 * @author dada
 */
public class GenreDetails implements IQueryResponseDTO {
    
    public String id;
    public String title;
    public String titleInPersian;
    public String createdAt;
    public String lastModifiedAt;
    public String creatorName;
    public String creatorId;
    public String updaterName;
    public String updaterId;
    public Set<GenreDetails> subGenres;
    
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
    
    @Override
    public GenreDetails filter(UserRole role) {
        this.subGenres.forEach(subGenre -> subGenre.filter(role));
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER:
                this.createdAt = null;
                this.lastModifiedAt = null;
                this.creatorId = null;
                this.creatorName = null;
                this.updaterId = null;
                this.updaterName = null;
                break;
            case SUBSCRIBER:
                this.title = null;
                this.createdAt = null;
                this.lastModifiedAt = null;
                this.creatorId = null;
                this.creatorName = null;
                this.updaterId = null;
                this.updaterName = null;
                break;
        }
        return this;
    }
    
}
