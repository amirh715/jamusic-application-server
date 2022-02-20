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
public class ProcessorMustBeTheEntityCreatorError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "پردازش کننده گزارش های مرتبط با هنرمندان و آثار باید ایجادکننده آنها باشند.";
    private static final int CODE = 403;
    
    public ProcessorMustBeTheEntityCreatorError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ProcessorMustBeTheEntityCreatorError(String message) {
        super(message, CODE);
    }
    
    public ProcessorMustBeTheEntityCreatorError(String message, String description) {
        super(message, CODE, description);
    }
    
}
