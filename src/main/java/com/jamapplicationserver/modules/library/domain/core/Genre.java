/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Genre extends ValueObject {
    
    public static final int MAX_ALLOWED_PARENT_GENRE_SIZE = 3;

    public final GenreTitle title;
    
    private final GenreTitle titleInPersian;
    
    private final Genre parentGenre;
    
    private Genre(GenreTitle title, GenreTitle titleInPersian, Genre parentGenre) {
        super();
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenre = parentGenre;
    }
    
    @Override
    public String getValue() {
        return this.title.toString();
    }
    
    public static Result<Genre> create(GenreTitle title, GenreTitle titleInPersian, Genre parentGenre) {
        
        if(
                parentGenre != null &&
                parentGenre.getSize() > MAX_ALLOWED_PARENT_GENRE_SIZE
        )
            return Result.fail(new ValidationError("Genre can have " + MAX_ALLOWED_PARENT_GENRE_SIZE + " max."));
        
        return Result.ok(new Genre(title, titleInPersian, parentGenre));
    }
    
    public final int getSize() {
        int size = 0;
        if(this.parentGenre == null)
            size = 1;
        else
            size = this.parentGenre.getSize() + 1;
        return size;
    }
    
    public final GenreTitle getTitle() {
        return this.title;
    }
    
    public final GenreTitle getTitleInPersian() {
        return this.titleInPersian;
    }
    
    public final Genre getParent() {
        return this.parentGenre;
    }
    
    public final boolean isRoot() {
        return parentGenre == null;
    }

    
}
