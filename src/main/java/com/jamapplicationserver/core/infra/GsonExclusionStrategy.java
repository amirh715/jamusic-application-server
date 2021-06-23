/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import com.google.gson.*;

/**
 *
 * @author amirhossein
 */
public class GsonExclusionStrategy implements ExclusionStrategy {
    
    private final Class<?> excludedClass;
    
    public GsonExclusionStrategy(Class<?> excludedClass) {
        this.excludedClass = excludedClass;
    }
    
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return excludedClass.equals(clazz);
    }
    
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return excludedClass.equals(f.getDeclaredClass());
    }
    
}
