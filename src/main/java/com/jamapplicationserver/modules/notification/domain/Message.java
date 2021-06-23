/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Message extends ValueObject {
    
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 4000;

    private final String value;
    
    private Message(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<Message> create(String message) {
        
        if(message == null)
            return Result.fail(new ValidationError("Notification message is required"));
        
        if(
                message.length() > MAX_LENGTH ||
                message.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError(""));
        
        return Result.ok(new Message(message));
    }
    
}
