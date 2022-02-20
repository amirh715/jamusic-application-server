/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class UserIsNotVerifiedError extends ConflictError {
    
    private static final int CODE = 231;
    private static final String DEFAULT_MESSAGE = "حساب کاربری تایید نشده است";
    
    public UserIsNotVerifiedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserIsNotVerifiedError(String message) {
        super(message, CODE);
    }
    
    public UserIsNotVerifiedError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
