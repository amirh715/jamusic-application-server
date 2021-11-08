/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services.exceptions;

/**
 *
 * @author dada
 */
public class NotificationCannotBeSentException extends Exception {
    
    public NotificationCannotBeSentException() {
        
    }
    
    public NotificationCannotBeSentException(Exception e) {
        super(e);
    }
    
    public NotificationCannotBeSentException(String message) {
        super(message);
    }
    
}
