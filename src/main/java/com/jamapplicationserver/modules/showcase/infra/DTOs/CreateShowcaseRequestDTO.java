/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.infra.DTOs;

import java.io.InputStream;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class CreateShowcaseRequestDTO extends DTOWithAuthClaims {
    
    public final String index;
    public final String title;
    public final String description;
    public final String route;
    public final InputStream image;
    
    public CreateShowcaseRequestDTO(
            String index,
            String title,
            String description,
            String route,
            InputStream image,
            UniqueEntityId creatorId,
            UserRole creatorRole
    ) {
        super(creatorId, creatorRole);
        this.index = index;
        this.title = title;
        this.description = description;
        this.route = route;
        this.image = image;
    }
    
    
}
