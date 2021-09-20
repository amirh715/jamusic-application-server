/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.NotFoundError;
import com.jamapplicationserver.core.domain.UniqueEntityId;

/**
 *
 * @author amirhossein
 */
public class UpdaterUserDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 204;
    private static final String DEFAULT_MESSAGE = "Updater user does not exist";
    
    public UpdaterUserDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UpdaterUserDoesNotExistError(String message) {
        super(message, CODE);
    }
    
}
