/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.BusinessError;

/**
 *
 * @author amirhossein
 */
public class UserCreatorDoesNotExistError extends BusinessError {
    
    private static final int CODE = 223;
    private static final String DEFAULT_MESSAGE = "کاربر ایجاد کننده وجود ندارد";
    
    public UserCreatorDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserCreatorDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    
    public UserCreatorDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
