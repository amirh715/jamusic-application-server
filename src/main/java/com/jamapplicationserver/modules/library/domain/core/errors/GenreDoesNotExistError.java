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
public class GenreDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "سبک وحود ندارد";
    private static final int CODE = 313;
    
    public GenreDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public GenreDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public GenreDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
