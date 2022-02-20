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
public class BandDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "گروه وجود ندارد";
    private static final int CODE = 310;
    
    public BandDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public BandDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public BandDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
