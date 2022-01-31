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
public class PasswordResetRequestDoesNotExistError extends ClientErrorError {
    
    private static final String DEFAULT_MESSAGE = "کد بازنشانی رمز اکانت درخواست نشده است. لطفا ابتدا درخواست بدهید";
    private static final int CODE = 111;
    
    public PasswordResetRequestDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public PasswordResetRequestDoesNotExistError(String message) {
        super(message, CODE);
    }
        
    public PasswordResetRequestDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
