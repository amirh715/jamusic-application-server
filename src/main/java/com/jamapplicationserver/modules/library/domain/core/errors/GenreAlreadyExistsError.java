/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GenreAlreadyExistsError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "سبکی با این عنوان وجود دارد";
    private static final int CODE = 312;
    
    public GenreAlreadyExistsError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public GenreAlreadyExistsError(String message) {
        super(message, CODE);
    }
    
    public GenreAlreadyExistsError(String message, String description) {
        super(message, CODE, description);
    }
    
}
