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
public class NotifTitle extends ValueObject {
    
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 40;
    
    private final String value;
    
    private NotifTitle(String value) {
        this.value = value;
    }
    
    private NotifTitle() {
        this.value = null;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<NotifTitle> create(String title) {
        
        if(title == null)
            return Result.fail(new ValidationError("نوع نوتیفیکیشن ضروری است"));
        
        if(
                title.length() > MAX_LENGTH ||
                title.length() < MIN_LENGTH
        ) return Result.fail(new ValidationError("عنوان نوتیفیکیشن باید بین ۱ تا ۴۰ کاراکتر باشد"));
        
        return Result.ok(new NotifTitle(title));
    }
    
    public static final Result<Optional<NotifTitle>> createNullable(String title) {
        
        if(title == null || title.isBlank() || title.isEmpty())
            return Result.ok(Optional.empty());
        
        final Result<NotifTitle> result = create(title);
        if(result.isFailure)
            return Result.fail(result.getError());
        else
            return Result.ok(Optional.of(result.getValue()));
        
    }
    
}
