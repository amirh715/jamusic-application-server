/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import java.util.UUID;
import java.time.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class EmailVerification extends ValueObject {
    
    public static final Duration TOKEN_VALID_DURATION = Duration.ofMinutes(10);

    private final UUID token;
    private final DateTime issuedAt;
    
    private EmailVerification() {
        this.token = UUID.randomUUID();
        this.issuedAt = DateTime.createNow();
    }
    
    private EmailVerification(UUID token, DateTime issuedAt) {
        this.token = token;
        this.issuedAt = issuedAt;
    }
    
    public static EmailVerification create() {
        return new EmailVerification();
    }
    
    public static Result<EmailVerification> create(UUID token, DateTime issuedAt) {
        
        final EmailVerification instance = new EmailVerification(token, issuedAt);
        
        return Result.ok(instance);
            
    }
    
    public UUID getToken() {
        return this.token;
    }
    
    public DateTime getIssuedAt() {
        return this.issuedAt;
    }
    
    public boolean isExpired() {
        final LocalDateTime validTill = issuedAt.getValue().plus(TOKEN_VALID_DURATION);
        return LocalDateTime.now().isAfter(validTill);
    }
    
    public boolean doesMatch(UUID providedToken) {
        return token.equals(providedToken);
    }
    
    @Override
    public String getValue() {
        return this.token.toString();
    }

}
