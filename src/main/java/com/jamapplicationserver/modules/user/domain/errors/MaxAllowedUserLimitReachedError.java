/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ConflictError;

/**
 *
 * @author dada
 */
public class MaxAllowedUserLimitReachedError extends ConflictError {
    
    private static final int CODE = 212;
    private static final String DEFAULT_MESSAGE = "سقف تعداد کاربران پر شده است";
    
    public MaxAllowedUserLimitReachedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public MaxAllowedUserLimitReachedError(String message) {
        super(message, CODE);
    }
    
    public MaxAllowedUserLimitReachedError(String message, String description) {
        super(message, CODE, description);
    }
    
}
