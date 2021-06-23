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
public abstract class AuthError extends BusinessError {

    private static final String DEFAULT_MESSAGE = "اجازه دسترسی وجود ندارد";
    
    public AuthError(String message, int code, String description) {
        super(message, code, description);
    }
    
    public AuthError(String message, int code) {
        super(message, code);
    }

}
