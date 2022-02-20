/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class ArtistMaxAllowedDurationExceededError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "حداکثر مدت زمان مجاز هنرمند گذشته است";
    private static final int CODE = 305;
    
    public ArtistMaxAllowedDurationExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ArtistMaxAllowedDurationExceededError(String message) {
        super(message, CODE);
    }
    
    public ArtistMaxAllowedDurationExceededError(String message, String description) {
        super(message, CODE, description);
    }
    
}
