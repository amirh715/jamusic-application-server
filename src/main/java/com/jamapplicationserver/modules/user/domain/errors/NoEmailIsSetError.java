/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ClientErrorError;

/**
 *
 * @author amirhossein
 */
public class NoEmailIsSetError extends ClientErrorError {
    
    private static final int CODE = 213;
    private static final String DEFAULT_MESSAGE = "ایمیلی در پروفایل نیست";
    
    public NoEmailIsSetError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public NoEmailIsSetError(String message) {
        super(message, CODE);
    }
    
    public NoEmailIsSetError(String message, String description) {
        super(message, CODE, description);
    }
    
}
