/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.repository.exceptions;

/**
 *
 * @author dada
 */
public class RemovingUserIsNotActiveException extends Exception {
    
    public RemovingUserIsNotActiveException() {
        super();
    }
    
    public RemovingUserIsNotActiveException(String message) {
        super(message);
    }
    
}
