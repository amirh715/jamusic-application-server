/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class NotificationIsAlreadySentError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "نوتیفیکیشن ارسال شده است.";
    private static final int CODE = 111;
    
    public NotificationIsAlreadySentError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public NotificationIsAlreadySentError(String message) {
        super(message, CODE);
    }
    
    public NotificationIsAlreadySentError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
