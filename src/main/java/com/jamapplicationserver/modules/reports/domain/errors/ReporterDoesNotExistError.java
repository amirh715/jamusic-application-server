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
public class ReporterDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "";
    private static final int CODE = 111;
    
    public ReporterDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ReporterDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public ReporterDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
