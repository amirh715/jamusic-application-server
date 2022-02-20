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
public class EmailVerificationLinkIsExpiredError extends ConflictError {

    private static final int CODE = 208;
    private static final String DEFAULT_MESSAGE = "لینک تایید ایمیل منقضی شده است. لطفا دوباره درخواست بدهید";
    
    public EmailVerificationLinkIsExpiredError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public EmailVerificationLinkIsExpiredError(String message) {
        super(message, CODE);
    }
        
    public EmailVerificationLinkIsExpiredError(String message, String description) {
        super(message, CODE, description);
    }

}
