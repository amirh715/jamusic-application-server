/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.CountryCode;
import com.jamapplicationserver.utils.PersianDigitsMapper;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 *
 * @author amirhossein
 */
public class MobileNo extends ValueObject<String> {
    
    private static final String PATTERN = "98([0-9]{10})$";

    private final String value;
    
    private MobileNo(String value) {
        this.value = value;
    }

    public static Result<MobileNo> create(String value) {
        
        if(value == null)
            return Result.fail(new ValidationError("User mobile is required"));
        
        // check validity with regular expressions.
        if(!Pattern.matches(MobileNo.PATTERN, value))
            return Result.fail(new ValidationError("Mobile number is not correct"));
        
        return Result.ok(new MobileNo(value));
        
    }
    
    private static CountryCode getCountryCallingCode(String value) throws CountryCodeException {
        
        Stream.of(CountryCode.values());
        
        return null;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public String getValueInPersian() {
        return PersianDigitsMapper.toPersian(value);
    }
    
    @Override
    public String toString() {
        return "Mobile No.: " + this.value;
    }
    
    
}
