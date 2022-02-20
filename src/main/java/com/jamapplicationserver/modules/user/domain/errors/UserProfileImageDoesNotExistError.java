/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.NotFoundError;

/**
 *
 * @author dada
 */
public class UserProfileImageDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 233;
    private static final String DEFAULT_MESSAGE = "پروفایل عکس وجود ندارد";
    
    public UserProfileImageDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserProfileImageDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public UserProfileImageDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
