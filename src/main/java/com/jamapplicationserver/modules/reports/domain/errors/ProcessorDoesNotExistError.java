/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class ProcessorDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "پردازش کننده وجود ندارد";
    private static final int CODE = 402;
    
    public ProcessorDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ProcessorDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public ProcessorDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
