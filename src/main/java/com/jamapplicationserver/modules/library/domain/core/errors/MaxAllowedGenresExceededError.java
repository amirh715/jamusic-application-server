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
public class MaxAllowedGenresExceededError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "";
    private static final int CODE = 111;
    
    public MaxAllowedGenresExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public MaxAllowedGenresExceededError(String message) {
        super(message, CODE);
    }
    
    public MaxAllowedGenresExceededError(String message, String description) {
        super(message, CODE, description);
    }
    
}
