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
public class AudioMaxAllowedDurationExceededError extends ClientErrorError {
    
    private static final String DEFAULT_MESSAGE = "MAx allowed duration";
    private static final int CODE = 111;
    
    public AudioMaxAllowedDurationExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AudioMaxAllowedDurationExceededError(String message) {
        super(message, CODE);
    }
    
    public AudioMaxAllowedDurationExceededError(String message, String description) {
        super(message, CODE, description);
    }
    
}
