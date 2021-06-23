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
public class Title extends ValueObject {
    
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 80;
    
    private final String title;
    
    private Title(String title) {
        this.title = title.trim();
    }
    
    @Override
    public String getValue() {
        return this.title;
    }
    
    public static final Result<Title> create(String title) {
        
        if(title == null)
            return Result.fail(new ValidationError(""));
        
        if(
                title.length() > MAX_LENGTH ||
                title.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("Title must be " + MIN_LENGTH + " to " + MAX_LENGTH + " characters long."));
        
        return Result.ok(new Title(title));
        
    }
    
    
    
}
