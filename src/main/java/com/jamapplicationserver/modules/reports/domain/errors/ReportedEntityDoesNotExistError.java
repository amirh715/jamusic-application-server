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
public class ReportedEntityDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "هنرمند/اثر گزارش شده وجود ندارد";
    private static final int CODE = 410;
    
    public ReportedEntityDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ReportedEntityDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public ReportedEntityDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
