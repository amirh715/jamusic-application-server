/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.NotFoundError;

/**
 *
 * @author amirhossein
 */
public class UserDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 201;
    private static final String DEFAULT_MESSAGE = "کاربر وجود ندارد";
    
    public UserDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public UserDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
