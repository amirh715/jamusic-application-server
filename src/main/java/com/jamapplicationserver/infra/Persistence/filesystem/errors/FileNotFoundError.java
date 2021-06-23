/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.filesystem.errors;

import com.jamapplicationserver.core.logic.NotFoundError;

/**
 *
 * @author amirhossein
 */
public class FileNotFoundError extends NotFoundError {
    
    private static final int CODE = 111;
    private static final String DEFAULT_MESSAGE = "File does not exist";
    
    public FileNotFoundError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public FileNotFoundError(String message) {
        super(message, CODE);
    }
    
    public FileNotFoundError(String message, String description) {
        super(message, CODE, description);
    }
    
}
