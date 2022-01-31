/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ConflictError;

/**
 *
 * @author amirhossein
 */
public class UserAlreadyExistsError extends ConflictError {
    
    private static final int CODE = 202;
    private static final String DEFAULT_MESSAGE = "اکانتی با این شماره موبایل وجود دارد. لطفا از شماره موبایل دیگری استفاده کنید";
    
    public UserAlreadyExistsError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserAlreadyExistsError(String message) {
        super(message, CODE);
    }
        
    public UserAlreadyExistsError(String message, String description) {
        super(message, CODE, description);
    }
    
}
