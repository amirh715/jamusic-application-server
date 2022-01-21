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
    public String searchTerm;
    public Boolean published;
    public Boolean isFlagged;
    public Boolean hasImage;
    public Set<UniqueEntityId> genreIds;
    public Rate rateFrom;
    public Rate rateTo;
    public Long totalPlayedCountFrom;
    public Long totalPlayedCountTo;
    public Long durationFrom;
    public Long durationTo;
    
    public UniqueEntityId artistId;
    public ReleaseDate releaseDateFrom;
    public ReleaseDate releaseDateTill;
    
    public UniqueEntityId creatorId;
    public UniqueEntityId updaterId;
    public DateTime createdAtFrom;
    public DateTime createdAtTill;
    public DateTime lastModifiedAtFrom;
    public DateTime lastModifiedAtTill;
    
    public Integer limit;
    public Integer offset;
    
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
            Long durationFrom,
            Long durationTo,
            UniqueEntityId artistId,
            ReleaseDate releaseDateFrom,
            ReleaseDate releaseDateTill,
            UniqueEntityId creatorId,
            UniqueEntityId updaterId,
            DateTime createdAtFrom,
            DateTime createdAtTill,
            DateTime lastModifiedAtFrom,
            DateTime lastModifiedAtTill,
            Integer limit,
            Integer offset
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
        this.durationFrom = durationFrom;
        this.durationTo = durationTo;
        this.artistId = artistId;
        this.releaseDateFrom = releaseDateFrom;
        this.releaseDateTill = releaseDateTill;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.limit = limit == null ? 30 : limit;
        this.offset = offset == null ? 0 : offset;
    }
    
    public LibraryEntityFilters() {
        
    }
    
}
