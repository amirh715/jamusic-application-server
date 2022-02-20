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
public class AccountVerificationRequestDoesNotExistError extends ConflictError {
    
    private static final int CODE = 204;
    private static final String DEFAULT_MESSAGE = "درخواست تایید اکانت نکرده اید";
    
    public AccountVerificationRequestDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AccountVerificationRequestDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public AccountVerificationRequestDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
