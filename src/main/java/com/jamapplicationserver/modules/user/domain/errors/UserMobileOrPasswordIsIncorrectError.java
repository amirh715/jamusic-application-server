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
public class UserMobileOrPasswordIsIncorrectError extends ClientErrorError {
    
    private static final int CODE = 111;
    
    public UserMobileOrPasswordIsIncorrectError() {
        super("Mobile or password is incorrect", CODE);
    }
    
}
