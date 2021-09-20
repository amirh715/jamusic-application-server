/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import java.util.*;
import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersRequestDTO implements IDTO {
    
    public final String searchTerm;
    public final String type;
    public final Set<String> genreIds;
    public final double rateFrom;
    public final double rateTo;
    public final List<String> tags;
    public final Boolean flagged;
    public final Boolean published;
    public final Boolean hasImage;
    public final String creatorId;
    public final String updaterId;
    public final String createdAtFrom;
    public final String createdAtTill;
    public final String lastModifiedAtFrom;
    public final String lastModifiedAtTill;
    
    public GetLibraryEntitiesByFiltersRequestDTO(
            String searchTerm,
            String type,
            String genres,
            String rateFrom,
            String rateTo,
            String tags,
            Boolean flagged,
            Boolean published,
            Boolean hasImage,
            String creatorId,
            String updaterId,
            String createdAtFrom,
            String createdAtTill,
            String lastModifiedAtFrom,
            String lastModifiedAtTill
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
            
        this.searchTerm = searchTerm;
        this.type = type;
        this.genreIds = serializer.deserialize(tags, new TypeToken<Set<String>>(){});
        this.rateFrom = rateFrom != null ? Double.valueOf(rateFrom) : 0.0;
        this.rateTo = rateTo != null ? Double.valueOf(rateTo) : 5.0;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
        this.tags = serializer.deserialize(tags, new TypeToken<List<String>>(){});
        this.flagged = flagged != null ? flagged : null;
        this.published = published != null ? published : null;
        this.hasImage = hasImage != null ? hasImage : null;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;

    }
    
}
