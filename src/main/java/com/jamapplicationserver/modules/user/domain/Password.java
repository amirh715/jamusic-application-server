/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;
import org.apache.commons.codec.digest.*;

/**
 *
 * @author amirhossein
 */
public class Password extends ValueObject {
    
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 300;
    
    public final boolean hashed;
    private final String value;
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    private Password(String value, boolean hashed) {
        this.value = value;
        this.hashed = hashed;
    }
    
    public static Result<Password> create(String password, boolean hashed) {
        
        if(password == null) return Result.fail(new ValidationError("رمز ضروری است"));
        
        if(hashed == true) { // password is hashed.
            return Result.ok(new Password(password, hashed));
        } else { // password is NOT hashed.
            
            if(
                password.length() > Password.MAX_LENGTH ||
                password.length() < Password.MIN_LENGTH
            ) { // password length is not within limits.
                
                return Result.fail(new ValidationError("رمز باید بین  " +
                    Password.MIN_LENGTH + " تا " + Password.MAX_LENGTH
                    + " کاراکتر باشد"
                ));
                
            } else { // password length is within limits.
                // hash password.
                final String passwordHash = Password.hashWithSHA256(password);
                return Result.ok(new Password(passwordHash, true));
            }
            
        }
        
    }
    
    private static String hashWithSHA256(String plainPassword) {
        String passwordHash = new DigestUtils("SHA3-256").digestAsHex(plainPassword);
        return passwordHash;
    }
    
    @Override
    public boolean equals(Object password) {
        if(password == this) return true;
        if(!(password instanceof Password)) return false;
        Password p = (Password) password;
        return p.getValue().equals(this.value) && p.hashed == this.hashed;
    }
    
    @Override
    public int hashCode() {
        
        return 0;
    }
    
}
