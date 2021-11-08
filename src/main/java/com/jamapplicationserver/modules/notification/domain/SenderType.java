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
public enum SenderType {
    
    USER("USER", "کاربر"),
    SYS("SYS", "سیستم");
    
    private final String value;
    private final String valueInPersian;
    
    private SenderType(String value, String valueInPersian) {
        this.value = value;
        this.valueInPersian = valueInPersian;
    }
    
    public static final Stream<SenderType> stream() {
        return Stream.of(SenderType.values());
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getValueInPersian() {
        return this.valueInPersian;
    }
    
    public boolean isSystem() {
        return this.value.equals(SYS.value);
    }
    
    public boolean isUser() {
        return this.value.equals(USER.value);
    }
    
    public static final Result<SenderType> create(String value) {
        if(value == null) return Result.fail(new ValidationError("Notification sender type."));
        final boolean isValid =
                SenderType.stream().anyMatch(type -> value.equals(type.getValue()));
        return isValid ? Result.ok(SenderType.valueOf(value)) :
                Result.fail(new ValidationError("Notification sender type is invalid"));
    }
    
}
