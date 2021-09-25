/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class ReleaseDate extends ValueObject<YearMonth> {
    
    private static final YearMonth MIN_VALUE = YearMonth.of(1500, 1);
    private static final YearMonth MAX_VALUE = YearMonth.now().plusMonths(1);
    
    private final YearMonth value;
    
    private ReleaseDate(YearMonth value) {
        this.value = value;
    }
    
    @Override
    public YearMonth getValue() {
        return this.value;
    }
    
    public static final Result<ReleaseDate> create(YearMonth value) {
        
        if(value == null) return Result.fail(new ValidationError("Release date is required"));
        
        if(
                value.isBefore(MIN_VALUE) ||
                value.isAfter(MAX_VALUE)
        ) return Result.fail(new ValidationError("Release date is invalid"));
        
        return Result.ok(new ReleaseDate(value));
    }
    
    public static final Result<ReleaseDate> create(Date value) {
        
        if(value == null) return Result.fail(new ValidationError("Release date is required"));
        
        final YearMonth yearMonth = YearMonth.from(value.toInstant());
        if(
                yearMonth.isAfter(MIN_VALUE) ||
                yearMonth.isBefore(MAX_VALUE)
        ) return Result.fail(new ValidationError("Release date is invalid"));
        
        return Result.ok(new ReleaseDate(yearMonth));
    }
    
    public static final Result<ReleaseDate> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Release date is required"));
        
        try {
            final YearMonth yearMonth = YearMonth.parse(value);
            if(
                    yearMonth.isBefore(MIN_VALUE) ||
                    yearMonth.isAfter(MAX_VALUE)
            ) return Result.fail(new ValidationError("Release date is invalid"));

            return Result.ok(new ReleaseDate(yearMonth));
        } catch(DateTimeParseException e) {
            return Result.fail(new ValidationError("Release date is invalid"));
        }
    }
    
    
}
