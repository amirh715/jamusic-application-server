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
    
    private static final String DEFAULT_MESSAGE = "کد بازنشانی رمز هر دو دقیقه یکبار ارسال می شود. لطفا منتظر باشید";
    private static final int CODE = 217;
    
    public PasswordResetCodeIsNotExpiredError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public PasswordResetCodeIsNotExpiredError(String message) {
        super(message, CODE);
    }
    
    public PasswordResetCodeIsNotExpiredError(String message, String description) {
        super(message, CODE, description);
    }
    
}
