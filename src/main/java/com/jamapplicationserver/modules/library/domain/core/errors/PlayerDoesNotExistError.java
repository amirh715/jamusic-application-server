/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.errors;

import com.jamapplicationserver.core.logic.NotFoundError;

/**
 *
 * @author dada
 */
public class PlayerDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "کاربر وجود ندارد";
    private static final int CODE = 323;
    
    public PlayerDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public PlayerDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public PlayerDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
