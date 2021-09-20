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
    
    public final DateTime createdAtFrom;
    public final DateTime createdAtTill;
    public final DateTime lastModifiedAtFrom;
    public final DateTime lastModifiedAtTill;
    
    public final String searchTerm;
    
    public final LibraryEntityType type;
    
    public final double rateFrom;
    public final double rateTill;
    
    public final Boolean published;
    
    public final Boolean isFlagged;
    
    public final Boolean hasImage;
    
    public final Set<UniqueEntityId> genreIds;
    
    public final UniqueEntityId creatorId;
    
    public final UniqueEntityId updaterId;
    
    public LibraryEntityFilters(
            DateTime createdAtFrom,
            DateTime createdAtTill,
            DateTime lastModifiedAtFrom,
            DateTime lastModifiedAtTill,
            String searchTerm,
            LibraryEntityType type,
            double rateFrom,
            double rateTill,
            Boolean published,
            Boolean isFlagged,
            Boolean hasImage,
            Set<UniqueEntityId> genreIds,
            UniqueEntityId creatorId,
            UniqueEntityId updaterId
    ) {
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.searchTerm = searchTerm;
        this.type = type;
        this.rateFrom = rateFrom;
        this.rateTill = rateTill;
        this.published = published != null ? published : null;
        this.isFlagged = isFlagged != null ? isFlagged : null;
        this.hasImage = hasImage != null ? hasImage : null;
        this.genreIds = genreIds;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
        
    }
    
}
