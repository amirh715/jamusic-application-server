/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.BusinessError;

/**
 *
 * @author amirhossein
 */
public class EmailVerificationLinkIsInvalidError extends BusinessError {
    
    private static final int CODE = 209;
    private static final String DEFAULT_MESSAGE = "لینک تایید ایمیل درست نیست";
    
    public EmailVerificationLinkIsInvalidError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public EmailVerificationLinkIsInvalidError(String message) {
        super(message, CODE);
    }
        
    public EmailVerificationLinkIsInvalidError(String message, String description) {
        super(message, CODE, description);
    }
    
}
