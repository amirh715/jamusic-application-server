/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.logic.NotFoundError;

/**
 *
 * @author amirhossein
 */
public class UserDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 201;
    
    public UserDoesNotExistError() {
        super("User does not exist.", CODE);
    }
    
    public UserDoesNotExistError(UniqueEntityId id) {
        super("User with ID '" + id.toString() + "' does not exist.", CODE);
    }
    
    public UserDoesNotExistError(String message) {
        super(message, CODE);
    }
    
}
