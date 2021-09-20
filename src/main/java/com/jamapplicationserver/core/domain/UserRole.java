/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.util.stream.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public enum UserRole {
    
    ADMIN("ADMIN", "مدیر سیستم"),
    LIBRARY_MANAGER("LIBRARY_MANAGER", "مدیر مخزن"),
    SUBSCRIBER("SUBSCRIBER", "مشترک");
    
    private final String value;
    private final String valueInPersian;
    
    private UserRole(String value, String valueInPersian) {
        this.value = value;
        this.valueInPersian = valueInPersian;
    }
    
    public static final Stream<UserRole> stream() {
        return Stream.of(UserRole.values());
    }
    
    public static final Result<UserRole> create(String value) {
        if(value == null) return Result.fail(new ValidationError("Role is required"));
        final boolean isValid =
                UserRole.stream().anyMatch(role -> value.equals(role.getValue()));
        return isValid ? Result.ok(UserRole.valueOf(value)) : Result.fail(new ValidationError("Invalid role."));
    }
    
    public final String getValue() {
        return this.value;
    }
    
    public final String getValueInPersian() {
        return this.valueInPersian;
    }
    
    public boolean isAdmin() {
        return this.equals(UserRole.ADMIN);
    }
    
    public boolean isLibraryManager() {
        return this.equals(UserRole.LIBRARY_MANAGER);
    }
    
    public boolean isSubscriber() {
        return this.equals(UserRole.SUBSCRIBER);
    }

}
