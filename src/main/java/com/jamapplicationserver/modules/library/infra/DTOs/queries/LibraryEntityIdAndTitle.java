/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;

/**
 *
 * @author dada
 */
public class LibraryEntityIdAndTitle implements IQueryResponseDTO {
    
    public final String id;
    public final String title;
    
    protected LibraryEntityIdAndTitle(String id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public static final LibraryEntityIdAndTitle create(LibraryEntityModel model) {
        return new LibraryEntityIdAndTitle(
                model.getId().toString(),
                model.getTitle()
        );
    }
    
    @Override
    public LibraryEntityIdAndTitle filter(UserRole role) {
        return this;
    }
    
    
}
