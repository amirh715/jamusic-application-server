/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class Message extends ValueObject<String> {
    
    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 200;
    
    private final String value;
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    private Message(String value) {
        this.value = value;
    }
    
    public static final Result<Message> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Report message is required"));
        
        if(
                value.length() > MAX_LENGTH ||
                value.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("Report message is invalid"));
        
        return Result.ok(new Message(value));
    }
    
}
