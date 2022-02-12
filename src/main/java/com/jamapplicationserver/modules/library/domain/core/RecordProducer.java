/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.Optional;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class RecordProducer extends ValueObject {
    
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;
    
    private final String value;
    
    private RecordProducer(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<RecordProducer> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Producer is required"));
        
        if(
                value.length() > MAX_LENGTH ||
                value.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("مقدار تهیه کننده باید بین ۱ تا ۵۰ کاراکتر باشد"));
        
        return Result.ok(new RecordProducer(value));
    }
    
    public static final Result<Optional<RecordProducer>> createNullable(String value) {
        
        if(value == null || value.isBlank() || value.isEmpty())
            return Result.ok(Optional.empty());
        
        final Result<RecordProducer> result = create(value);
        if(result.isFailure)
            return Result.fail(result.getError());
        else
            return Result.ok(Optional.of(result.getValue()));
        
    }
    
}
