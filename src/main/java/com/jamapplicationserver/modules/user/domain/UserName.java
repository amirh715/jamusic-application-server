package com.jamapplicationserver.modules.user.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 * 
 */
public class UserName extends ValueObject<String> {
        
    private static final int MAX_LENGTH = 80;
    private static final int MIN_LENGTH = 1;
    
    private final String value;
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    private UserName(String value) {
        this.value = value;
    }
    
    public static Result<UserName> create(String value) {
        if(value == null) return Result.fail(new ValidationError("اسم کاربر ضروری است"));
        if(
            value.length() > UserName.MAX_LENGTH ||
            value.length() < UserName.MIN_LENGTH
        ) return Result.fail(new ValidationError("اسم کاربر باید بین " + UserName.MIN_LENGTH
            +  " تا " + UserName.MAX_LENGTH + " کاراکتر باشد"));
        return Result.ok(new UserName(value));
    }
    
    @Override
    public String toString() {
        return "Name: " + this.value;
    }
    
}
