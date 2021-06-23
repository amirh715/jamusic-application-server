/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import java.time.*;
import java.security.SecureRandom;


/**
 *
 * @author amirhossein
 */
public class UserVerification extends ValueObject {
    
    private static int CODE_MIN = 1000;
    private static int CODE_MAX = 9999;
    public static final Duration CODE_VALID_DURATION = Duration.ofMinutes(5);

    private final DateTime issuedAt;
    private final int code;
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    private UserVerification(
            DateTime issuedAt,
            int code
    ) {
        this.issuedAt = issuedAt;
        this.code = code;
    }
    
    private UserVerification() {
        this.issuedAt = DateTime.createNow();
        this.code = generateCode();
    }
    
    private static int generateCode() {
        final SecureRandom rand = new SecureRandom();
        final int code = rand.nextInt(CODE_MAX - CODE_MIN + 1) + CODE_MIN;
        return code;
    }
    
    public boolean isExpired() {
        final LocalDateTime validTill = this.issuedAt.getValue().plus(CODE_VALID_DURATION);
        return LocalDateTime.now().isAfter(validTill);
    }
    
    public Result matchAndExpire(int providedCode) {
        // verification is expired
        if(this.isExpired()) return Result.fail(new AccountVerificationCodeIsExpiredError());
        // check whether the verification code matches or not
        final boolean doesMatch = Integer.valueOf(this.code).equals(providedCode);
        return doesMatch == true ? Result.ok() : Result.fail(new AccountVerificationCodeIsIncorrectError()) ;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public DateTime getIssuedAt() {
        return this.issuedAt;
    }
    
    public static UserVerification create() {
        
        return new UserVerification();
    }
    
    public static UserVerification create(DateTime issuedAt, int code) {
        
        return new UserVerification(issuedAt, code);
    }
    
}
