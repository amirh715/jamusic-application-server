/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import java.util.stream.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public enum UserState {
    
    ACTIVE("ACTIVE", "فعال"),
    NOT_VERIFIED("NOT_VERIFIED", "تایید نشده"),
    BLOCKED("BLOCKED", "مسدود");
    
    private final String value;
    private final String valueInPersian;
    
    private UserState(String value, String valueInPersian) {
        this.value = value;
        this.valueInPersian = valueInPersian;
    }
    
    public static final Stream<UserState> stream() {
        return Stream.of(UserState.values());
    }
    
    public static final Result<UserState> create(String value) {
        if(value == null) return Result.fail(new ValidationError("User state is required"));
        final boolean isValid =
                UserState.stream().anyMatch(state -> value.equals(state.getValue()));
        return isValid ? Result.ok(UserState.valueOf(value)) : Result.fail(new ValidationError("Invalid state"));
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getValueInPersian() {
        return this.valueInPersian;
    }
    
    public final boolean isActive() {
        return this.value.equals(ACTIVE.value);
    }
    
    public final boolean equalsTo(UserState userState) {
        return this.equals(userState);
    }
    
}
