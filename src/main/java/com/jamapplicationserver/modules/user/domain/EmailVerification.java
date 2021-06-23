/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import java.time.Duration;
import java.net.*;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
public class EmailVerification extends ValueObject {
    
    public static final Duration LINK_VALID_DURATION = Duration.ofMinutes(10);

    private final URL link;
    private final DateTime issuedAt;
    
    private EmailVerification(URL link) {
        this.link = link;
        this.issuedAt = DateTime.createNow();
    }
    
    private EmailVerification(URL link, DateTime issuedAt) {
        this.link = link;
        this.issuedAt = issuedAt;
    }
    
    public static EmailVerification create() {
        try {
            System.err.println("NO ERROR");
            return new EmailVerification(generateLink());
        } catch(MalformedURLException e) {
            System.out.println("MALFOREMED");
            return null;
        }
    }
    
    public static Result<EmailVerification> create(URL link, DateTime issuedAt) {
        
        final EmailVerification instance = new EmailVerification(link, issuedAt);
        
        return Result.ok(instance);
            
    }
    
    private static URL generateLink() throws MalformedURLException {
        final URL link = new URL("https://api.jamusic.ir/v1/user/reset_link/" + UUID.randomUUID().toString());
        return link;
    }
    
    public URL getLink() {
        return this.link;
    }
    
    public DateTime getIssuedAt() {
        return this.issuedAt;
    }
    
    public boolean isExpired() {
        final LocalDateTime validTill = this.issuedAt.getValue().plus(LINK_VALID_DURATION);
        return LocalDateTime.now().isAfter(validTill);
    }
    
    public boolean doesMatch(URL providedLink) {
        return this.link.equals(providedLink);
    }
    
    @Override
    public String getValue() {
        return this.link.toString();
    }
    
    
    
}
