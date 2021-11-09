/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
@Embeddable
public class UserEmailVerificationModel implements Serializable {
    
    @Column(name="email_verification_link", unique=true)
    private String link;
    
    @Column(name="email_verification_link_issued_at")
    private LocalDateTime issuedAt;
    
    public String getLink() {
        return this.link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public LocalDateTime getIssuedAt() {
        return this.issuedAt;
    }
    
    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
    
}
