/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.util.*;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class ImageFileFormat extends ValueObject {
    
    private static final Set<String> VALID_TYPES = Set.of("JPG");
    
    private final String value;
    
    private ImageFileFormat(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<ImageFileFormat> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Image file type is required"));
        
        if(!VALID_TYPES.contains(value)) return Result.fail(new ValidationError("Invalid image type"));
        
        return Result.ok(new ImageFileFormat(value));
    }
    
}
