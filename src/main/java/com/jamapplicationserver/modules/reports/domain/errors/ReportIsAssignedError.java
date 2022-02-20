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
public class ReportIsAssignedError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "گزارش ارسال شده است";
    private static final int CODE = 407;
    
    public ReportIsAssignedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ReportIsAssignedError(String message) {
        super(message, CODE);
    }
    
    public ReportIsAssignedError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
