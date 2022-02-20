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
public class ReportIsProcessedError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "گزارش پردازش شده است";
    private static final int CODE = 409;
    
    public ReportIsProcessedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ReportIsProcessedError(String message) {
        super(message, CODE);
    }
    
    public ReportIsProcessedError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
