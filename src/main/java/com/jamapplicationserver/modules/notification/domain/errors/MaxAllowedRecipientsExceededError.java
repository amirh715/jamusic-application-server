/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class MaxAllowedRecipientsExceededError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "";
    private static final int CODE = 111;
    
    public MaxAllowedRecipientsExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public MaxAllowedRecipientsExceededError(String message) {
        super(message, CODE);
    }
    
    public MaxAllowedRecipientsExceededError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
