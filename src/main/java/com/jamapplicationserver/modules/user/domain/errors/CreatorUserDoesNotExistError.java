/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.NotFoundError;
import com.jamapplicationserver.core.domain.UniqueEntityId;

/**
 *
 * @author amirhossein
 */
public class CreatorUserDoesNotExistError extends NotFoundError {
    
    private static final int CODE = 203;
    private static final String DEFAULT_MESSAGE = "کاربر ایجادکننده وجود ندارد";
    
    public CreatorUserDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public CreatorUserDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public CreatorUserDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
        
}
