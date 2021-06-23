/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="login_audits", schema="jamschema")
public class LoginAuditModel implements Serializable {
    
    @Id
    @Column(name="id")
    private UUID id;
    
    @Column(name="ip_address")
    private String ipAddress;
    
    @Column(name="was_successful")
    private boolean wasSuccessful;
    
    @Column(name="failure_reason")
    private String failureReason;
    
    @Column(name="attempted_at")
    private LocalDateTime attemptedAt;
    
    @Column(name="platform")
    private String platform;
    
    @Column(name="os")
    private String os;
    
    @ManyToOne(optional=true)
    private UserModel user;
    
    public LoginAuditModel() {
        
    }
    
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public boolean wasSuccessful() {
        return this.wasSuccessful;
    }
    
    public void setWasSuccessful(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }
    
    public String getFailureReason() {
        return this.failureReason;
    }
    
    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
    
    public LocalDateTime getAttemptedAt() {
        return this.attemptedAt;
    }
    
    public void setAttemptedAt(LocalDateTime attemptedAt) {
        this.attemptedAt = attemptedAt;
    }
    
    public String getPlatform() {
        return this.platform;
    }
    
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    public String getOs() {
        return this.os;
    }
    
    public void setOs(String os) {
        this.os = os;
    }
    
    public UserModel getUser() {
        return this.user;
    }
    
    public void setUser(UserModel user) {
        this.user = user;
    }
    
}
