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
public class AccountVerificationCodeIsIncorrectError extends ConflictError {
    
    private static final int CODE = 221;
    private static final String DEFAULT_MESSAGE = "کد تایید اکانت درست نیست";
    
    public AccountVerificationCodeIsIncorrectError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AccountVerificationCodeIsIncorrectError(String message) {
        super(message, CODE);
    }
        
    public AccountVerificationCodeIsIncorrectError(String message, String description) {
        super(message, CODE, description);
    }
    
}
