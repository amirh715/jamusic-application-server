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
    
    public final Boolean published;
    
    public final Boolean flagged;
    
    public final Boolean hasImage;
    
    public final GenreList genres;
    
    public final UniqueEntityID creatorId;
    
    public final UniqueEntityID updaterId;
    
    public LibraryEntityFilters(
            DateTime createdAtFrom,
            DateTime createdAtTill,
            DateTime lastModifiedAtFrom,
            DateTime lastModifiedAtTill,
            String searchTerm,
            LibraryEntityType type,
            Boolean published,
            Boolean flagged,
            Boolean hasImage,
            GenreList genres,
            UniqueEntityID creatorId,
            UniqueEntityID updaterId
    ) {
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.searchTerm = searchTerm;
        this.type = type;
        this.published = published != null ? published : true;
        this.flagged = flagged;
        this.hasImage = hasImage;
        this.genres = genres;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
    }
    
}
