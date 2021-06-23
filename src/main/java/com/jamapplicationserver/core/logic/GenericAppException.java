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
public class GenericAppException extends Exception {
    
    private static final int CODE = 100;
    
    public GenericAppException() {
        super("An error occurred. (" + CODE + ")");
    }
    
    public GenericAppException(Exception e) {
        super(e.getMessage() + " (" + CODE + ")");
    }
    
}
