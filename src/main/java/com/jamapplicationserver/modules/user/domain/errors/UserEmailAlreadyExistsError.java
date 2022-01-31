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
public class UserEmailAlreadyExistsError extends ConflictError {
    
    private static final int CODE = 203;
    private static final String DEFAULT_MESSAGE = "این ایمیل در سیستم وجود دارد. لطفا از ایمیل دیگری استفاده کنید";
    
    public UserEmailAlreadyExistsError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserEmailAlreadyExistsError(String message) {
        super(message, CODE);
    }
    
    public UserEmailAlreadyExistsError(String message, String description) {
        super(message, CODE, description);
    }
    
}
