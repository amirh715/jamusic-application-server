/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.entities;

import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class GenreIdAndTitle {
    
    public final String id;
    public final String title;
    
    protected GenreIdAndTitle(
            String id,
            String title
    ) {
        this.id = id;
        this.title = title;
    }
    
    public static GenreIdAndTitle create(GenreModel genre) {
        return new GenreIdAndTitle(
                genre.getId().toString(),
                genre.getTitleInPersian()
        );
    }
    
}
