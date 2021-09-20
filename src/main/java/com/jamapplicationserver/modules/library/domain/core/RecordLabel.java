/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class RecordLabel extends ValueObject {
    
    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 1;
    
    private String value;
    
    private RecordLabel(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<RecordLabel> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Record label is required"));
        if(
                value.length() > MAX_LENGTH ||
                value.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("Record label is invalid"));
        
        return Result.ok(new RecordLabel(value));
    }
    
}
