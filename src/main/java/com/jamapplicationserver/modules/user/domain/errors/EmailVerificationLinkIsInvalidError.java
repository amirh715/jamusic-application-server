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
    
    private static final String MESSAGE = "";
    private static final int CODE = 111;
    private static final String DESCRIPTION = "";
    
    public EmailVerificationLinkIsInvalidError() {
        super(MESSAGE, CODE, DESCRIPTION);
    }
    
}
