/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.logic;

/**
 *
 * @author dada
 */
public class UpdaterOrCreatorDoesNotExistError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "";
    private static final int CODE = 111;
    
    public UpdaterOrCreatorDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UpdaterOrCreatorDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public UpdaterOrCreatorDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
