/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Email extends ValueObject<String> {
    
    private final String value;
    
    private Email(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static Result<Email> create(String email) {
        
        if(email == null)
            return Result.fail(new ValidationError("Email is required."));
        
        return Result.ok(new Email(email));
    }
    
}
