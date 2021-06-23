/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import java.util.UUID;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="users_email_verification", schema="jamschema")
public class UserEmailVerificationModel implements Serializable {
    
    @Id
    @Column(name="user_id")
    private UUID userID;
    
    @Column(name="link", unique=true, nullable=false)
    private String link;
    
    @Column(name="issued_at")
    private LocalDateTime issuedAt;
    
    @OneToOne
    @MapsId
    private UserModel user;
    
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
    
    public UserModel getUser() {
        return this.user;
    }
    
    public void setUser(UserModel user) {
        this.user = user;
    }
    
}
