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
public class AudioNotSupportedError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "فایل صوتی پشتیبانی نشده است";
    private static final int CODE = 309;
    
    public AudioNotSupportedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AudioNotSupportedError(String message) {
        super(message, CODE);
    }
    
    public AudioNotSupportedError(String message, String description) {
        super(message, CODE, description);
    }
    
}
