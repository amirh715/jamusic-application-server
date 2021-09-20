/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.infra.DTOs;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class CreateShowcaseRequestDTO implements IDTO {
    
    public final String index;
    public final String title;
    public final String description;
    public final String route;
    public final String image;
    public final String creatorId;
    
    public CreateShowcaseRequestDTO(
            String index,
            String title,
            String description,
            String route,
            String image,
            String creatorId
    ) {
        this.index = index;
        this.title = title;
        this.description = description;
        this.route = route;
        this.image = image;
        this.creatorId = creatorId;
    }
    
    
}
