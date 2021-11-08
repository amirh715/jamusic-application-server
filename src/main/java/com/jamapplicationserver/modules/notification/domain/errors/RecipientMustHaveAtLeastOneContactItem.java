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
public class RecipientMustHaveAtLeastOneContactItem extends ClientErrorError {
    
    private static final int CODE = 101;
    private static final String DEFAULT_MESSAGE = "Recipient must have at least one contact info";
    
    public RecipientMustHaveAtLeastOneContactItem() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public RecipientMustHaveAtLeastOneContactItem(String message) {
        super(message, CODE);
    }
    
    public RecipientMustHaveAtLeastOneContactItem(String message, String description) {
        super(message, CODE, description);
    }
    
}
