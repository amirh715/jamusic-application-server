/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUsersByFilters;

import java.util.Optional;

/**
 *
 * @author amirhossein
 */
public class GetUsersByFiltersRequestDTO {
    
    public final String searchTerm;
    public final String createdAtFrom;
    public final String createdAtTill;
    public final String lastModifiedAtFrom;
    public final String lastModifiedAtTill;
    public final String hasImage;
    public final String hasEmail;
    public final String state;
    public final String role;
    public final String creatorId;
    public final String updaterId;
    
    public GetUsersByFiltersRequestDTO(
            String searchTerm,
            String createdAtFrom,
            String createdAtTill,
            String lastModifiedAtFrom,
            String lastModifiedAtTill,
            String hasImage,
            String hasEmail,
            String state,
            String role,
            String creatorId,
            String updaterId
    ) {
        this.searchTerm = searchTerm;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTill = createdAtTill;
        this.lastModifiedAtFrom = lastModifiedAtFrom;
        this.lastModifiedAtTill = lastModifiedAtTill;
        this.hasImage = hasImage;
        this.hasEmail = hasEmail;
        this.state = state;
        this.role = role;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
    }
    
    
}
