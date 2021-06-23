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
public abstract class BusinessError {

    private static final String DEFAULT_MESSAGE = "Something went wrong...";
    
    public final String message;
    public final String type;
    public final int code;
    public final String description;
    
    protected BusinessError(String message, int code) {
        this.message = message != null ? message + " (" + code + ")." : DEFAULT_MESSAGE;
        this.type = this.getClass().getSimpleName();
        this.code = code;
        this.description = null;
    }
    
    protected BusinessError(String message, int code, String description) {
        this.message = message != null ? message + " (" + code + ")." : DEFAULT_MESSAGE;
        this.type = this.getClass().getSimpleName();
        this.code = code;
        this.description = description;
    }
    
}
