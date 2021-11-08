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
public class Flag extends ValueObject {
    
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 200;
    
    private final String flagNote;
    
    private Flag(
            String flagNote
    ) {
        this.flagNote = flagNote;
    }
    
    @Override
    public String getValue() {
        return this.flagNote;
    }
    
    public static Result<Flag> create(
            String flagNote
    ) {
        
        if(flagNote == null) return Result.fail(new ValidationError("Flag note is required."));
        
        if(
                flagNote.length() > MAX_LENGTH ||
                flagNote.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("Flag note must be " + MIN_LENGTH + " to " + MAX_LENGTH + " characters long."));
        
        return Result.ok(new Flag(flagNote));
    }
    
    /**
     * Value object is considered null if value is null, an empty string, or blank.
     * @param value
     * @return 
     */
    public static Result<Optional<Flag>> createNullable(String value) {

        if(value == null || value.isBlank() || value.isEmpty())
            return Result.ok(Optional.empty());
        
        final Result<Flag> result = create(value);
        if(result.isFailure)
            return Result.fail(result.getError());
        else
            return Result.ok(Optional.of(result.getValue()));
        
    }

}
