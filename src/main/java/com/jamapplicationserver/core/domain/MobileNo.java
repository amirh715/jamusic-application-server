/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import com.jamapplicationserver.core.logic.*;
import com.google.i18n.phonenumbers.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class MobileNo extends ValueObject<String> {
    
    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    private final Phonenumber.PhoneNumber value;
    
    private MobileNo(Phonenumber.PhoneNumber value) {
        this.value = value;
    }

    public static Result<MobileNo> create(String value) {
        
        if(value == null)
            return Result.fail(new ValidationError("User mobile is required"));
        
        try {
            
            final Phonenumber.PhoneNumber phoneNo = phoneUtil.parse(value, "IR");
            
            final boolean isValid = phoneUtil.isValidNumber(phoneNo);
            if(!isValid) return Result.fail(new ValidationError("Mobile number is not correct"));
            
            final PhoneNumberUtil.PhoneNumberType type = phoneUtil.getNumberType(phoneNo);
            if(type.equals(PhoneNumberUtil.PhoneNumberType.FIXED_LINE))
                return Result.fail(new ValidationError("Mobile number can not be a fixed line number"));
            
            return Result.ok(new MobileNo(phoneNo));
            
        } catch(NumberParseException e) {
            LogService.getInstance().error(e);
            return Result.fail(new ValidationError("Mobile number is not correct"));
        }
        
    }
    
    public int getCountryCode() {
        return this.value.getCountryCode();
    }
    
    public boolean isIranianPhoneNumber() {
        return this.value.getCountryCode() == 98;
    }
    
    @Override
    public String getValue() {
        return phoneUtil.format(this.value, PhoneNumberUtil.PhoneNumberFormat.E164);
    }
    
    public String getNationalValue() {
        return phoneUtil.format(this.value, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
    }

    @Override
    public String toString() {
        return "Mobile No.: " + this.value;
    }
    
    
}
