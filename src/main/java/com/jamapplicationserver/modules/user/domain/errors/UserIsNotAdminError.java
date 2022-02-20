/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ForbiddenError;

/**
 *
 * @author dada
 */
public class UserIsNotAdminError extends ForbiddenError {
    
    private static final int CODE = 230;
    private static final String DEFAULT_MESSAGE = "کاربر مدیر سیستم نیست";
    
    public UserIsNotAdminError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserIsNotAdminError(String message) {
        super(message, CODE);
    }
    
    public UserIsNotAdminError(String message, String description) {
        super(message, CODE, description);
    }

}
