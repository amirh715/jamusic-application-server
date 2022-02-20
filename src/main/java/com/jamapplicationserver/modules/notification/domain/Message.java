/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import java.util.*;
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
            return Result.fail(new ValidationError("پیام نوتیفیکیشن ضروری است"));
        
        if(
                message.length() > MAX_LENGTH ||
                message.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("طول پیام نوتیفیکیشن باید بین ۱ تا ۴۰۰۰ کاراکتر باشد"));
        
        return Result.ok(new Message(message));
    }
    
    public static final Result<Optional<Message>> createNullable(String message) {

        if(message == null || message.isBlank() || message.isEmpty())
            return Result.ok(Optional.empty());
        
        final Result<Message> result = create(message);
        if(result.isFailure)
            return Result.fail(result.getError());
        else
            return Result.ok(Optional.of(result.getValue()));
        
    }
    
}
