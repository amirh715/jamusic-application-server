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
public abstract class UnauthorizedError extends BusinessError {
    
    protected UnauthorizedError(String message, int code) {
        super(message, code);
    }
    
    protected UnauthorizedError(String message, int code, String description) {
        super(message, code, description);
    }
    
}
