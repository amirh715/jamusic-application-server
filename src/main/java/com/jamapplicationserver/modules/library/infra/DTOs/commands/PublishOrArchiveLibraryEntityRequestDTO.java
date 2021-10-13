/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.commands;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class PublishOrArchiveLibraryEntityRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    public final Boolean publish;
    public final Boolean cascadePublishCommandToArtistsArtworks;
    
    public PublishOrArchiveLibraryEntityRequestDTO(
            String id,
            String publish,
            String cascadePublishCommandToArtistsArtworks,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        super(updaterId, updaterRole);
        this.id = id;
        this.publish = Boolean.parseBoolean(publish);
        this.cascadePublishCommandToArtistsArtworks =
                Boolean.parseBoolean(cascadePublishCommandToArtistsArtworks);
    }
    
}
