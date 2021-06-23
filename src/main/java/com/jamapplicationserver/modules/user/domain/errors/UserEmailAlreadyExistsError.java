/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ConflictError;
import com.jamapplicationserver.modules.user.domain.Email;

/**
 *
 * @author amirhossein
 */
public class UserEmailAlreadyExistsError extends ConflictError {
    
    private static final int CODE = 203;
    
    public UserEmailAlreadyExistsError() {
        super("User email address already exists in the database.", CODE);
    }
    
    public UserEmailAlreadyExistsError(Email email) {
        super("User email address '" + email.getValue() + "' already exists in the database.", CODE);
    }
    
}
