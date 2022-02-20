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
public class SentNotificationCannotBeRemovedError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "نوتیفیکشن ارسال شده نمی تواند حذف شود.";
    private static final int CODE = 507;
    
    public SentNotificationCannotBeRemovedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public SentNotificationCannotBeRemovedError(String message) {
        super(message, CODE);
    }
    
    public SentNotificationCannotBeRemovedError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
