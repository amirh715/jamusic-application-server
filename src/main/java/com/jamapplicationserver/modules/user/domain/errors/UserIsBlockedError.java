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
public class UserIsBlockedError extends ConflictError {
    
    private static final int CODE = 213;
    private static final String DEFAULT_MESSAGE = "اکانت شما مسدود است";
    
    public UserIsBlockedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserIsBlockedError(String message) {
        super(message, CODE);
    }
        
    public UserIsBlockedError(String message, String description) {
        super(message, CODE, description);
    }
    
}
