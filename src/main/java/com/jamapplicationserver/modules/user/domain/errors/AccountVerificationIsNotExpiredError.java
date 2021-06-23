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
public class AccountVerificationIsNotExpiredError extends ConflictError {
    
    private static final String MESSAGE = "User verification code is not expired";
    private static final int CODE = 111;
    private static final String DESCRIPTION = "";
    
    public AccountVerificationIsNotExpiredError() {
        super(MESSAGE, CODE, DESCRIPTION);
    }
    
}
