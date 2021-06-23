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
public class ValidationError extends ClientErrorError {
    
    public static final int CODE = 101;
    
    public ValidationError(String message) {
        super(message, CODE);
    }
    
    public ValidationError() {
        super("Validation Error.", CODE);
    }

}
