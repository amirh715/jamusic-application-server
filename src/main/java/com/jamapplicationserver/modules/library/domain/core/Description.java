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
public class Description extends ValueObject {
    
    public static final int MIN_LENGTH = 4; // 40
    public static final int MAX_LENGTH = 400;
    
    private final String description;
    
    private Description(String description) {
        this.description = description.trim();
    }
    
    @Override
    public String getValue() {
        return this.description;
    }
    
    public static Result<Description> create(String description) {
        
        if(description == null) return Result.fail(new ValidationError("Description is required."));
        
        if(
                description.length() > MAX_LENGTH ||
                description.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("Description must be " + MIN_LENGTH + " to " + MAX_LENGTH + " characters long."));
        
        return Result.ok(new Description(description));
        
    }
    
}
