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
 * @author dada
 */
public class MusicFileFormat extends ValueObject {
    
    private final String value;
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    private MusicFileFormat(String value) {
        this.value = value;
    }
    
    public static final Result<MusicFileFormat> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError());
        
        
        return Result.ok(new MusicFileFormat(value));
    }
    
}
