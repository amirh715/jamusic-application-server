/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.ClientErrorError;

/**
 *
 * @author dada
 */
public class UserCannotLoginThroughClient extends ClientErrorError {
    
    private static final int CODE = 222;
    private static final String DEFAULT_MESSAGE = "امکان ورود از طریق این نرم افزار برای شما وجود ندارد";
    
    public UserCannotLoginThroughClient() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public UserCannotLoginThroughClient(String message) {
        super(message, CODE);
    }
    
    public UserCannotLoginThroughClient(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
