/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
@Embeddable
public class UserEmailVerificationModel implements Serializable {
    
    @Column(name="email_verification_token", unique=true)
    private UUID token;
    
    @Column(name="email_verification_link_issued_at")
    private LocalDateTime issuedAt;
    
    public UUID getToken() {
        return this.token;
    }
    
    public void setToken(UUID token) {
        this.token = token;
    }
    
    public LocalDateTime getIssuedAt() {
        return this.issuedAt;
    }
    
    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
    
}
