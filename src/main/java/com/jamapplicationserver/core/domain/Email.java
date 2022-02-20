/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.util.*;
import java.util.regex.*;
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
            return Result.fail(new ValidationError("ایمیل ضروری است"));
        
        final Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(email);
        final boolean matchFound = matcher.find();
        if(!matchFound) return Result.fail(new ValidationError("ایمیل درست نیست"));
        
        return Result.ok(new Email(email));
    }
    
    public static Result<Optional<Email>> createNullable(String value) {
        
        if(value == null || value.isBlank() || value.isEmpty())
            return Result.ok(Optional.empty());
        
        final Result<Email> result = create(value);
        if(result.isFailure)
            return Result.fail(result.getError());
        else
            return Result.ok(Optional.of(result.getValue()));
        
    }
    
}
