/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author amirhossein
 */
public class LibraryEntityFilters {
    
    public LibraryEntityType type;
    public final String searchTerm;
    public final Boolean published;
    public final Boolean isFlagged;
    public final Boolean hasImage;
    public final Set<UniqueEntityId> genreIds;
    public final Rate rateFrom;
    public final Rate rateTo;
    public final Long totalPlayedCountFrom;
    public final Long totalPlayedCountTo;
    
    public UniqueEntityId albumId;
    public UniqueEntityId artistId;
    public ReleaseDate releaseDateFrom;
    public ReleaseDate releaseDateTill;
    
    public final UniqueEntityId creatorId;
    public final UniqueEntityId updaterId;
    public final DateTime createdAtFrom;
    public final DateTime createdAtTill;
    public final DateTime lastModifiedAtFrom;
    public final DateTime lastModifiedAtTill;
    
    public LibraryEntityFilters(
            LibraryEntityType type,
            String searchTerm,
            Boolean published,
            Boolean isFlagged,
            Boolean hasImage,
            Set<UniqueEntityId> genreIds,
            Rate rateFrom,
            Rate rateTo,
            Long totalPlayedCountFrom,
            Long totalPlayedCountTo,
            UniqueEntityId albumId,
            UniqueEntityId artistId,
            ReleaseDate releaseDateFrom,
            ReleaseDate releaseDateTill,
            UniqueEntityId creatorId,
            UniqueEntityId updaterId,
            DateTime createdAtFrom,
            DateTime createdAtTill,
            DateTime lastModifiedAtFrom,
            DateTime lastModifiedAtTill
    ) {
        this.type = type;
        this.searchTerm = searchTerm;
        this.published = published;
        this.isFlagged = isFlagged;
        this.hasImage = hasImage;
        this.genreIds = genreIds;
        this.rateFrom = rateFrom;
        this.rateTo = rateTo;
        this.totalPlayedCountFrom = totalPlayedCountFrom;
        this.totalPlayedCountTo = totalPlayedCountTo;
        this.albumId = albumId;
        this.artistId = artistId;
        this.releaseDateFrom = releaseDateFrom;
        this.releaseDateTill = releaseDateTill;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
    }
    
}
