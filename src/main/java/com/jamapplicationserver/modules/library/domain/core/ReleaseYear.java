/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import java.time.Year;

/**
 *
 * @author dada
 */
public class ReleaseYear extends ValueObject {
    
    private static final Year MIN_VALUE = Year.of(1500);
    private static final Year MAX_VALUE = Year.now().plusYears(1);
    
    private final Year value;
    
    private ReleaseYear(Year value) {
        this.value = value;
    }
    
    @Override
    public Year getValue() {
        return this.value;
    }
    
    public static final Result<ReleaseYear> create(Year value) {
        
        if(value == null) return Result.fail(new ValidationError("Release year is required"));
        
        if(
                value.isBefore(MIN_VALUE) ||
                value.isAfter(MAX_VALUE)
        ) return Result.fail(new ValidationError("Release year is invalid"));
        
        return Result.ok(new ReleaseYear(value));
    }
    
    public static final Result<ReleaseYear> create(Integer value) {
        
        if(value == null) return Result.fail(new ValidationError("Release year is required"));
        
        final Year year = Year.of(value);
        if(
                year.isBefore(MIN_VALUE) ||
                year.isAfter(MAX_VALUE)
        ) return Result.fail(new ValidationError("Release year is invalid"));
        
        return Result.ok(new ReleaseYear(year));
    }
    
    public static final Result<ReleaseYear> create(String value) {
        
        
        return Result.ok(new ReleaseYear(Year.now()));
    }
    
    
}
