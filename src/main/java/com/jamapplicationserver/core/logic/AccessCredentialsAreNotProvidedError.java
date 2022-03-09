/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.logic;

/**
 *
 * @author dada
 */
public class AccessCredentialsAreNotProvidedError extends ForbiddenError {
    
    private static final String DEFAULT_MESSAGE = "المان های مورد نیاز برای بررسی دسترسی وجود ندارند";
    private static final int CODE = 999;
    
    public AccessCredentialsAreNotProvidedError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public AccessCredentialsAreNotProvidedError(String message) {
        super(message, CODE);
    }
    
    public AccessCredentialsAreNotProvidedError(String message, String description) {
        super(message, CODE, description);
    }
    
}
