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
public class UserCreatorDoesNotExistError extends BusinessError {
    
    public static final int CODE = 204;
    
    public UserCreatorDoesNotExistError() {
        super("User creating the new user does not exist.", CODE);
    }
    
    public UserCreatorDoesNotExistError(String message) {
        super(message, CODE);
    }
    
}
