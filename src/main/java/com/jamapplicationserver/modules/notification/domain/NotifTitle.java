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
public class NotifTitle extends ValueObject {
    
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 40;
    
    private final String value;
    
    private NotifTitle(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<NotifTitle> create(String title) {
        
        if(title == null)
            return Result.fail(new ValidationError("Notification title is required"));
        
        if(
                title.length() > MAX_LENGTH ||
                title.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError(""));
        
        return Result.ok(new NotifTitle(title));
    }
    
}
