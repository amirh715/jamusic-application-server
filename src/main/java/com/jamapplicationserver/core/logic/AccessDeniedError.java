/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.logic;

/**
 *
 * @author amirhossein
 */
public class AccessDeniedError extends AuthError {
    
    private static final int CODE = 100;
    private static final String DEFAULT_MESSAGE = "اجازه دسترسی وجود ندارد";
    
    public AccessDeniedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AccessDeniedError(String message, String description) {
        super(message, CODE, description);
    }
    
    public AccessDeniedError(String message) {
        super(message, CODE);
    }
    
}
