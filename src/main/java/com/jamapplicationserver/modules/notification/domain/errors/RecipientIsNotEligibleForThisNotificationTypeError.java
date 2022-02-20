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
public class RecipientIsNotEligibleForThisNotificationTypeError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "مخاطب امکان دریافت این نوع نوتیفیکیشن را ندارد.";
    private static final int CODE = 505;
    
    public RecipientIsNotEligibleForThisNotificationTypeError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public RecipientIsNotEligibleForThisNotificationTypeError(String message) {
        super(message, CODE);
    }
    
    public RecipientIsNotEligibleForThisNotificationTypeError(String message, String description) {
        super(message, CODE, description);
    }
    
}
