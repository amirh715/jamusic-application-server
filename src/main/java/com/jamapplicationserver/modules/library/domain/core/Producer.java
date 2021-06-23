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
public class Producer extends ValueObject {
    
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 80;
    
    private final String producer;
    
    private Producer(String producer) {
        this.producer = producer.trim();
    }
    
    @Override
    public String getValue() {
        return this.producer;
    }
    
    public static final Result<Producer> create(String producer) {
        
        if(producer == null)
            return Result.fail(new ValidationError("Producer is required."));
        
        if(
                producer.length() > MAX_LENGTH ||
                producer.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("Producer value must be " + MIN_LENGTH + " to " + MAX_LENGTH + " characters long."));
        
        return Result.ok(new Producer(producer));
        
    }
    
}
