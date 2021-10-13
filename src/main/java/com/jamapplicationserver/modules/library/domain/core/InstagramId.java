/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.net.*;
import java.util.regex.*;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class InstagramId extends ValueObject {
    
    private static final String PATTERN = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$";
    private final String value;
    
    private InstagramId(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public final URL getLink() {
        try {
            return new URL("instagram.com/" + value);
        } catch(MalformedURLException e) {
            return null;
        }
    }
    
    public static final Result<InstagramId> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError(""));
        
        final Pattern pattern = Pattern.compile(PATTERN);
        final Matcher matcher = pattern.matcher(value);
        boolean doesMatch = matcher.matches();
        
        if(!doesMatch) return Result.fail(new ValidationError("Instagram Id is invalid"));
        
        return Result.ok(new InstagramId(value));
    }
    
}
