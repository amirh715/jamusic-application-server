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
public class ProcessorMustProcessReportsAssignedToHerError extends ClientErrorError {
    
    private static final String DEFAULT_MESSAGE = "تنها پردازش کننده تعیین شده می تواند گزارش را پردازش کند.";
    private static final int CODE = 111;
    
    public ProcessorMustProcessReportsAssignedToHerError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ProcessorMustProcessReportsAssignedToHerError(String message) {
        super(message, CODE);
    }
    
    public ProcessorMustProcessReportsAssignedToHerError(String message, String description) {
        super(message, CODE, description);
    }
    
}
