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
public class EmailVerificationLinkIsNotExpiredError extends ConflictError {

    private static final int CODE = 210;
    private static final String DEFAULT_MESSAGE = "لینک تایید ایمیل هر ۱۰ دقیقه یکبار ارسال می شود. لطفا منتظر باشید";
    
    public EmailVerificationLinkIsNotExpiredError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public EmailVerificationLinkIsNotExpiredError(String message) {
        super(message, CODE);
    }
        
    public EmailVerificationLinkIsNotExpiredError(String message, String description) {
        super(message, CODE, description);
    }
    
}
