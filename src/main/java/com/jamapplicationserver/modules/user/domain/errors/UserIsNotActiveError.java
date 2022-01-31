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
public class UserIsNotActiveError extends ConflictError {

    private static final int CODE = 111;
    private static final String DEFAULT_MESSAGE = "اکانت شما فعال نیست";
    
    public UserIsNotActiveError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserIsNotActiveError(String message) {
        super(message, CODE);
    }
    
    public UserIsNotActiveError(String message, String description) {
        super(message, CODE, description);
    }
    
}
