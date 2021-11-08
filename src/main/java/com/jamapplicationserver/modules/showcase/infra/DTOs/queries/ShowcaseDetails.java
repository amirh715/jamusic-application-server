/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class ShowcaseDetails implements IQueryResponseDTO {
    
    public String id;
    public String index;
    public String title;
    public String description;
    public String route;
    public String interactionCount;
    public String createdAt;
    public String lastModifiedAt;
    
    public ShowcaseDetails(
            UUID id,
            int index,
            String title,
            String description,
            String route,
            long interactionCount,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt
    ) {
        this.id = id.toString();
        this.index = Integer.toString(index);
        this.title = title;
        this.description = description;
        this.route = route;
        this.interactionCount = Long.toString(interactionCount);
        this.createdAt = createdAt.toString();
        this.lastModifiedAt = lastModifiedAt.toString();
    }
    
    @Override
    public ShowcaseDetails filter(UserRole role) {
        
        
        return this;
    }
    
}
