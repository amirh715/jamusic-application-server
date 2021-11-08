/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.Optional;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Description extends ValueObject {
    
    public static final int MIN_LENGTH = 4; // 40
    public static final int MAX_LENGTH = 400;
    
    private final String description;
    
    private Description(String description) {
        this.description = description.trim();
    }
    
    private Description() {
        this.description = null;
    }
    
    @Override
    public String getValue() {
        return this.description;
    }
    
    public static Result<Description> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Description is required."));
        
        if(
                value.length() > MAX_LENGTH ||
                value.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("Description must be " + MIN_LENGTH + " to " + MAX_LENGTH + " characters long."));
        
        return Result.ok(new Description(value));
        
    }
    
    public static Result<Optional<Description>> createNullable(String value) {
        
        if(value == null || value.isBlank() || value.isEmpty())
            return Result.ok(Optional.empty());
        
        final Result<Description> result = create(value);
        if(result.isFailure)
            return Result.fail(result.getError());
        else
            return Result.ok(Optional.of(result.getValue()));
        
    }
    
}
