/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
@Embeddable
public class UserVerificationModel implements Serializable {
    
    @Column(name="account_verification_code")
    private int code;
    
    @Column(name="account_verification_code_issued_at")
    private LocalDateTime issued_at;

    public UserVerificationModel() {

    }
    
    public LocalDateTime getIssuedAt() {
        return this.issued_at;
    }
    
    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issued_at = issuedAt;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }

}
