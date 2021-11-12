/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class EmailVerificationLinkDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 111;
    private static final String DEFAULT_MESSAGE = "Email verification link is not valid";
    
    public EmailVerificationLinkDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public EmailVerificationLinkDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public EmailVerificationLinkDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
