/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.*;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class GenreList extends ValueObject {
    
    public static final int MAX_ALLOWED_GENRES = 2;
    
    private final Set<Genre> genres;
    
    @Override
    public Set<Genre> getValue() {
        return this.genres;
    }
    
    private GenreList(Set genres) {
        this.genres = genres;
    }
    
    public static final Result<GenreList> create(Set<Genre> genres) {
        
        if(genres == null || genres.isEmpty())
            return Result.fail(new ValidationError("Genres are required"));
        
        if(genres.size() > MAX_ALLOWED_GENRES)
            return Result.fail(new ValidationError("Max allowed genres is " + MAX_ALLOWED_GENRES));
        
        final Genre[] arr = new Genre[genres.size()];

        
        
        return Result.ok(new GenreList(genres));
    }
    
    
}
