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
public class UserMobileOrPasswordIsIncorrectError extends ConflictError {
    
    private static final int CODE = 111;
    
    public UserMobileOrPasswordIsIncorrectError() {
        super("شماره موبایل یا رمز درست نیست", CODE);
    }
    
}
