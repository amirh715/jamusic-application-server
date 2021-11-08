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
public class Lyrics extends ValueObject {
    
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 10000;
    
    private final String lyrics;
    
    @Override
    public String getValue() {
        return this.lyrics;
    }
    
    private Lyrics(String lyrics) {
        this.lyrics = lyrics;
    }
    
    public static final Result<Lyrics> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError(""));
        
        if(
                value.length() > MAX_LENGTH ||
                value.length() < MIN_LENGTH
        ) return Result.fail(
                new ValidationError("Lyrics must be between " + MIN_LENGTH + " to " + MAX_LENGTH + " characters long.")
        );
        
        return Result.ok(new Lyrics(value));
    }
    
    public static final Result<Optional<Lyrics>> createNullable(String value) {

        if(value == null || value.isBlank() || value.isEmpty())
            return Result.ok(Optional.empty());
        
        final Result<Lyrics> result = create(value);
        if(result.isFailure)
            return Result.fail(result.getError());
        else
            return Result.ok(Optional.of(result.getValue()));
        
    }
    
}
