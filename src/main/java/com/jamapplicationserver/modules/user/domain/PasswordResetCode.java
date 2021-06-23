/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import java.time.Duration;
import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
public class PasswordResetCode extends ValueObject {
    
    public static final int CODE_MIN = 1111;
    public static final int CODE_MAX = 9999;
    public static final Duration CODE_VALID_DURATION = Duration.ofMinutes(2);
    
    private final int code;
    private final DateTime issuedAt;

    @Override
    public String getValue() {
        return this.toString();
    }
    
    private PasswordResetCode(
            int code,
            DateTime issuedAt
    ) {
        this.code = code;
        this.issuedAt = issuedAt;
    }
    
    private PasswordResetCode() {
        this.code = generateCode();
        this.issuedAt = DateTime.createNow();
    }
    
    public static final PasswordResetCode create() {
        return new PasswordResetCode();
    }
    
    public static final Result<PasswordResetCode> create(
            int code,
            DateTime issuedAt
    ) {
        return Result.ok(new PasswordResetCode(code, issuedAt));
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
        
        if(this.isExpired()) return Result.fail(new PasswordResetCodeIsIncorrectError());

        final boolean doesMatch = Integer.valueOf(this.code).equals(providedCode);
        return doesMatch == true ? Result.ok() : Result.fail(new PasswordResetCodeIsIncorrectError());
        
    }
    
    public int getCode() {
        return this.code;
    }
    
    public DateTime getIssuedAt() {
        return this.issuedAt;
    }

}
