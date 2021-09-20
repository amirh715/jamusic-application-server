/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class PublishArchiveLibraryEntityRequestDTO implements IDTO {
    
    public final String id;
    public final Boolean published;
    public final Boolean cascadeToAll;
    
    public PublishArchiveLibraryEntityRequestDTO(
            String id,
            String published,
            String cascadeToAll
    ) {
        this.id = id;
        this.published = Boolean.valueOf(published);
        this.cascadeToAll = Boolean.valueOf(cascadeToAll);
    }
    
}
