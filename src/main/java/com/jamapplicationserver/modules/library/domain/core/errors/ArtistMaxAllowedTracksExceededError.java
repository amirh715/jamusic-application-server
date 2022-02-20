/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.errors;

import com.jamapplicationserver.core.logic.ConflictError;

/**
 *
 * @author amirhossein
 */
public class ArtistMaxAllowedTracksExceededError extends ConflictError {

    private static final String DEFAULT_MESSAGE = "حداکثر تعداد مجاز آهنگ های هنرمند گذشته است";
    private static final int CODE = 306;
    
    public ArtistMaxAllowedTracksExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ArtistMaxAllowedTracksExceededError(String message) {
        super(message, CODE);
    }
        
    public ArtistMaxAllowedTracksExceededError(String message, String description) {
        super(message, CODE, description);
    }

}
