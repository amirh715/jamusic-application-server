/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import java.util.*;
import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersRequestDTO extends DTOWithAuthClaims {
    
    public final String type;
    public final String searchTerm;
    public final Boolean published;
    public final Boolean flagged;
    public final Boolean hasImage;
    public final Set<String> genreIds;
    public final Double rateFrom;
    public final Double rateTo;
    public final Long totalPlayedCountFrom;
    public final Long totalPlayedCountTo;
    public final Long durationFrom;
    public final Long durationTo;
    public final String artistId;
    public final String releaseDateFrom;
    public final String releaseDateTill;
    public final String creatorId;
    public final String updaterId;
    public final String createdAtFrom;
    public final String createdAtTill;
    public final String lastModifiedAtFrom;
    public final String lastModifiedAtTill;
    
    public GetLibraryEntitiesByFiltersRequestDTO(
            String type,
            String searchTerm,
            String published,
            String flagged,
            String hasImage,
            String genreIds,
            String rateFrom,
            String rateTo,
            String totalPlayedCountFrom,
            String totalPlayedCountTo,
            String durationFrom,
            String durationTo,
            String artistId,
            String releaseDateFrom,
            String releaseDateTill,
            String creatorId,
            String updaterId,
            String createdAtFrom,
            String createdAtTill,
            String lastModifiedAtFrom,
            String lastModifiedAtTill,
            UniqueEntityId accessorId,
            UserRole accessorRole
    ) {
        
        super(accessorId, accessorRole);
        
        final ISerializer serializer = Serializer.getInstance();
            
        this.type = type;
        this.searchTerm = searchTerm;
        this.published = published != null ? Boolean.parseBoolean(published) : null;
        this.flagged = flagged != null ? Boolean.parseBoolean(flagged) : null;
        this.hasImage = hasImage != null ? Boolean.parseBoolean(hasImage) : null;
        this.genreIds = serializer.deserialize(genreIds, new TypeToken<Set<String>>(){});
        this.rateFrom = rateFrom != null ? Double.parseDouble(rateFrom) : null;
        this.rateTo = rateTo != null ? Double.parseDouble(rateTo) : null;
        this.totalPlayedCountFrom = totalPlayedCountFrom != null ? Long.parseLong(totalPlayedCountFrom) : null;
        this.totalPlayedCountTo = totalPlayedCountTo != null ? Long.parseLong(totalPlayedCountTo) : null;
        this.durationFrom = durationFrom != null ? Long.parseLong(durationFrom) : null;
        this.durationTo = durationTo != null ? Long.parseLong(durationTo) : null;
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
