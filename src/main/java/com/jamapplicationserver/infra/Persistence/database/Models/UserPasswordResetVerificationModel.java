/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="users_password_reset_code")
public class UserPasswordResetVerificationModel implements Serializable {
    
    @Id
    @Column(name="user_id")
    private UUID userId;
    
    @Column(name="code", nullable=false)
    private int code;
    
    @Column(name="issued_at")
    private LocalDateTime issuedAt;
    
    @OneToOne
    @MapsId
    private UserModel user;
    
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
    
    public UserModel getUser() {
        return this.user;
    }
    
    public void setUser(UserModel user) {
        this.user = user;
    }

}
