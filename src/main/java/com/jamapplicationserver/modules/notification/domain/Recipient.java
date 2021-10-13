/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import java.util.regex.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.ValueObject;

/**
 *
 * @author amirhossein
 */
public abstract class Recipient extends ValueObject {
    
    private final String value;
    
    protected Recipient(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<Recipient> create(String value) {
        if(value == null) return Result.fail(new ValidationError("Recipient is required"));
        
        Recipient recipient;
        
        if(isFCMRecipient(value))
            recipient = new FCMRecipient(value);
        else if(isSMSRecipient(value))
            recipient = new SMSRecipient(value);
        else if(isEmailRecipient(value))
            recipient = new EmailRecipient(value);
        else
            return Result.fail(new ValidationError("Invalid recipient"));
        
        return Result.ok(recipient);
    }
    
    private static boolean isFCMRecipient(String recipient) {
        if(recipient == null) return false;
        final Pattern pattern = Pattern.compile("");
        final Matcher matcher = pattern.matcher(recipient);
        return matcher.matches();
    }
    
    private static boolean isSMSRecipient(String recipient) {
        if(recipient == null) return false;
        final Pattern pattern = Pattern.compile("");
        final Matcher matcher = pattern.matcher(recipient);
        return matcher.matches();
    }
    
    private static boolean isEmailRecipient(String recipient) {
        if(recipient == null) return false;
        final Pattern pattern = Pattern.compile("");
        final Matcher matcher = pattern.matcher(recipient);
        return matcher.matches();
    }
    
}
