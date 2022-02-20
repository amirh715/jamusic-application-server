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
public class AtleastOneRecipientDoesNotExistError extends ConflictError {
    
    private static final int CODE = 501;
    private static final String DEFAULT_MESSAGE = "دسته کم یک مخاطب وجود ندارد.";
    
    public AtleastOneRecipientDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AtleastOneRecipientDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public AtleastOneRecipientDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
