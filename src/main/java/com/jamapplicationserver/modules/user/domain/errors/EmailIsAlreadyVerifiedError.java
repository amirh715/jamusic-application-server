/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ConflictError;

/**
 *
 * @author amirhossein
 */
public class EmailIsAlreadyVerifiedError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "ایمیل تایید شده است";
    private static final int CODE = 206;
    
    public EmailIsAlreadyVerifiedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public EmailIsAlreadyVerifiedError(String message) {
        super(message, CODE);
    }
    
    public EmailIsAlreadyVerifiedError(String message, String description) {
        super(message, CODE, description);
    }
    
}
