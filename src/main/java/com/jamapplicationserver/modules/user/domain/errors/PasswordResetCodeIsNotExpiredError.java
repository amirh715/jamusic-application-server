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
public class PasswordResetCodeIsNotExpiredError extends ClientErrorError {
    
    private static final String MESSAGE = "کد بازنشانی رمز هنوز منقضی نشده است";
    private static final int CODE = 111;
    private static final String DESCRIPTION = "";
    
    public PasswordResetCodeIsNotExpiredError() {
        super(MESSAGE, CODE);
    }
    
    public PasswordResetCodeIsNotExpiredError(String message) {
        super(message, CODE);
    }
    
    public PasswordResetCodeIsNotExpiredError(String message, String description) {
        super(message, CODE, description);
    }
    
}
