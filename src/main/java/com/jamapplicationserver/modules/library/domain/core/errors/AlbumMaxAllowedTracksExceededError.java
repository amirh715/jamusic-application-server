/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.errors;

import com.jamapplicationserver.core.logic.ConflictError;

/**
 *
 * @author dada
 */
public class AlbumMaxAllowedTracksExceededError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "حداکثر تعداد مجاز آهنگ های یک آلبوم ۱۵۰ عدد است";
    private static final int CODE = 302;
    
    public AlbumMaxAllowedTracksExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AlbumMaxAllowedTracksExceededError(String message) {
        super(message, CODE);
    }
    
    public AlbumMaxAllowedTracksExceededError(String message, String description) {
        super(message, CODE, description);
    }
    
}
