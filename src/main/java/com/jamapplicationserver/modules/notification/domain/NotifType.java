/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import java.util.stream.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public enum NotifType {
    
    FCM("FCM", "FCM"),
    SMS("SMS", "پیامک"),
    EMAIL("EMAIL", "ایمیل");
    
    private final String value;
    private final String valueInPersian;
    
    private NotifType(String value, String valueInPersian) {
        this.value = value;
        this.valueInPersian = valueInPersian;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getValueInPersian() {
        return this.valueInPersian;
    }
    
    public boolean isFCM() {
        return this.value.equals(FCM.value);
    }
    
    public boolean isSMS() {
        return this.value.equals(SMS.value);
    }
    
    public boolean isEmail() {
        return this.value.equals(EMAIL.value);
    }
    
    public static final Stream<NotifType> stream() {
        return Stream.of(NotifType.values());
    }
    
    public static final Result<NotifType> create(String value) {
        if(value == null) return Result.fail(new ValidationError("نوع نوتیفیکیشن ضروری است"));
        final boolean isValid =
                NotifType.stream().anyMatch(type -> value.equals(type.getValue()));
        return isValid ? Result.ok(NotifType.valueOf(value)) : Result.fail(new ValidationError("نوع نوتیفیکیشن درست نیست"));
    }
    
}
