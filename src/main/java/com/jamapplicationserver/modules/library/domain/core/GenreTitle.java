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
public class GenreTitle extends ValueObject {
    
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 40;
    
    private final String title;
    
    private GenreTitle(String title) {
        this.title = title;
    }
    
    public static Result<GenreTitle> create(String title) {
        
        if(title == null) return Result.fail(new ValidationError(""));
        
        if(
                title.length() > GenreTitle.MAX_LENGTH ||
                title.length() < GenreTitle.MIN_LENGTH
        ) return Result.fail(new ValidationError(""));
        
        return Result.ok(new GenreTitle(title));
    }
    
    @Override
    public String getValue() {
        return this.title;
    }
    
}
