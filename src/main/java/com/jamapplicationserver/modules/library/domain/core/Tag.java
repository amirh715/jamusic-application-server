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
public class Tag extends ValueObject<String> {
    
    public static final int MIN_LENGTH = 0;
    public static final int MAX_LENGTH = 20;
    
    private final String value;
    
    private Tag(String tag) {
        this.value = tag.trim();
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<Tag> create(String tag) {
        
        if(tag == null) return Result.fail(new ValidationError("Tag is required."));
        
        if(
                tag.length() < MIN_LENGTH ||
                tag.length() > MAX_LENGTH
        ) return Result.fail(new ValidationError("Tag length must be between " + MIN_LENGTH + " to " + MAX_LENGTH + " characters long."));
        
        if(tag.contains(" "))
            return Result.fail(new ValidationError("Tag value should not contain space character."));
        
        return Result.ok(new Tag(tag));
    }
    
}
