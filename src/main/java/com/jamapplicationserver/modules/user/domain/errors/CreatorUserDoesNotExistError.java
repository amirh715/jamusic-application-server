/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.NotFoundError;
import com.jamapplicationserver.core.domain.UniqueEntityID;

/**
 *
 * @author amirhossein
 */
public class CreatorUserDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 203;
    
    public CreatorUserDoesNotExistError(UniqueEntityID id) {
        super("Creator user with id " + id.toString() + " does not exist", CODE);
    }
    
    public CreatorUserDoesNotExistError() {
        super("Creator user does not exist", CODE);
    }
    
}
