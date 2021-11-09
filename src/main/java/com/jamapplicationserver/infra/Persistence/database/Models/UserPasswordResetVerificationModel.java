/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
//@Entity
//@Table(name="users_password_reset_code")
@Embeddable
public class UserPasswordResetVerificationModel implements Serializable {
    
    @Column(name="password_reset_code")
    private int code;
    
    @Column(name="password_reset_issued_at")
    private LocalDateTime issuedAt;
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public LocalDateTime getIssuedAt() {
        return this.issuedAt;
    }
    
    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

}
