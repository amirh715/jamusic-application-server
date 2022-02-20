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
public class ParentGenreDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "سبک بالادست وجود ندارد";
    private static final int CODE = 322;
    
    public ParentGenreDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ParentGenreDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public ParentGenreDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
