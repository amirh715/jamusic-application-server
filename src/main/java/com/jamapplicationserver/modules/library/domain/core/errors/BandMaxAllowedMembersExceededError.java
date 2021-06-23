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
public class BandMaxAllowedMembersExceededError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "";
    private static final int CODE = 111;
    
    public BandMaxAllowedMembersExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public BandMaxAllowedMembersExceededError(String message) {
        super(message, CODE);
    }
    
    public BandMaxAllowedMembersExceededError(String message, String description) {
        super(message, CODE, description);
    }
    
}
