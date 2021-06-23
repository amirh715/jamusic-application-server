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
    
    private static final String MESSAGE = "Password reset code is not requested";
    private static final int CODE = 111;
    private static final String DESCRIPTION = "";
    
    public PasswordResetRequestDoesNotExistError() {
        super(MESSAGE, CODE, DESCRIPTION);
    }
    
}
