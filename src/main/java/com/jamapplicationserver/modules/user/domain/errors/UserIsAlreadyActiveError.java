/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ConflictError;

/**
 *
 * @author amirhossein
 */
public class UserIsAlreadyActiveError extends ConflictError {

    private static final int CODE = 211;
    
    public UserIsAlreadyActiveError() {
        super("User is already active", CODE);
    }
    
}
