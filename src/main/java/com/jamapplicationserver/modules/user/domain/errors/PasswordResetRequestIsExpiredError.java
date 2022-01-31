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
public class PasswordResetRequestIsExpiredError extends ClientErrorError {
    
    private static final String DEFAULT_MESSAGE = "کد بازنشانی رمز اکانت منقضی شده است. لطفا دوباره درخواست بدهید";
    private static final int CODE = 111;
    
    public PasswordResetRequestIsExpiredError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public PasswordResetRequestIsExpiredError(String message) {
        super(message, CODE);
    }
    
    public PasswordResetRequestIsExpiredError(String message, String description) {
        super(message, CODE, description);
    }
    
}
