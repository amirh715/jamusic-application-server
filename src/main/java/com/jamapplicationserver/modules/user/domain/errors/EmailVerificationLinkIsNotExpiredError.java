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

    private static final int CODE = 111;
    
    public EmailVerificationLinkIsNotExpiredError() {
        super("", CODE);
    }
    
}
