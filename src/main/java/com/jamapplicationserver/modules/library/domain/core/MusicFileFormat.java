/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class MusicFileFormat extends ValueObject {
    
    private static final Set<String> allowedFormat =
            Set.of("mp3", "mpeg")
            .stream()
            .map(f -> f.toUpperCase())
            .collect(Collectors.toSet());
    
    private final String value;
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    private MusicFileFormat(String value) {
        this.value = value;
    }
    
    public static final Result<MusicFileFormat> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Audio file format is required"));
        
        final boolean isAllowedFormat = allowedFormat.contains(value.toUpperCase());
        
        if(!isAllowedFormat) return Result.fail(new AudioNotSupportedError());
        
        return Result.ok(new MusicFileFormat(value));
    }
    
}
