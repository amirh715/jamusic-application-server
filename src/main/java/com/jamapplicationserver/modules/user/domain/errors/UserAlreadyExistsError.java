/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ConflictError;
import com.jamapplicationserver.core.domain.UniqueEntityId;

/**
 *
 * @author amirhossein
 */
public class UserAlreadyExistsError extends ConflictError {
    
    private static final int CODE = 202;
    
    public UserAlreadyExistsError(UniqueEntityId id) {
        super("User with id " + id.toString() + " already exists.", CODE);
    }
    
    public UserAlreadyExistsError() {
        super("User already exists in the database.", CODE);
    }
    
}
