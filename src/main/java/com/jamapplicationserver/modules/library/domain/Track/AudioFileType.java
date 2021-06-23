/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Track;

import java.util.*;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class AudioFileType extends ValueObject {
    
    private static final Set<String> VALID_TYPES = Set.of("MP3");
    
    private final String value;
    
    private AudioFileType(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<AudioFileType> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("File type is required"));
        
        if(!VALID_TYPES.contains(value)) return Result.fail(new ValidationError("Invalid file type"));
        
        return Result.ok(new AudioFileType(value));
        
    }
    
}
