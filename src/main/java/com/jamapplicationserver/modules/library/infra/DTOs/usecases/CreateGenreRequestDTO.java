/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import com.google.gson.reflect.TypeToken;
import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class CreateGenreRequestDTO implements IDTO {
    
    public final String title;
    public final String titleInPersian;
    public final String parentGenreId;
    public final String creatorId;
    
    public CreateGenreRequestDTO(
            String title,
            String titleInPersian,
            String parentGenreId,
            String creatorId
    ) {
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenreId = parentGenreId;
        this.creatorId = creatorId;
    }
    
}
