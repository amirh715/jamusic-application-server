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
public class UpdaterUserDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 220;
    private static final String DEFAULT_MESSAGE = "کاربری که این رکورد را بروز می کند وجود ندارد";
    
    public UpdaterUserDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UpdaterUserDoesNotExistError(String message) {
        super(message, CODE);
    }
    
}
