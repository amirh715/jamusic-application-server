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
public class FCMToken extends ValueObject {
    
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 500;
    
    private final String value;
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    private FCMToken(String value) {
        this.value = value;
    }
    
    public static final Result<FCMToken> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("توکن FCM ضروری است"));
        
        if(
                value.length() > MAX_LENGTH ||
                value.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("طول توکن بین ۱ تا ۵۰۰ کاراکتر است"));
        
        return Result.ok(new FCMToken(value));
        
    }
    
}
