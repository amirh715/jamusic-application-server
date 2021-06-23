/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="users_verification", schema="jamschema")
public class UserVerificationModel implements Serializable {

    @Id
    @Column(name="user_id")
    private UUID userID;
    
    @Column(name="code", nullable=false)
    private int code;
    
    @Column(name="issued_at", nullable=false)
    private LocalDateTime issued_at;
    
    @OneToOne
    @MapsId
    private UserModel user;

    public UserVerificationModel() {

    }
    
    public UUID getUserID() {
        return this.userID;
    }
    
    public void setUserID(UUID userID) {
        this.userID = userID;
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
    
    public UserModel getUser() {
        return this.user;
    }
    
    public void setUser(UserModel user) {
        this.user = user;
    }
    
    @Override
    public boolean equals(Object verif) {
        System.out.println("UserVerificationModel.equals");
        if(verif == this)
            return true;
        if(!(verif instanceof UserVerificationModel))
            return false;
        UserVerificationModel v = (UserVerificationModel) verif;
        return v.getUserID().equals(this.userID);
    }
    
    @Override
    public int hashCode() {
        return this.userID.hashCode();
    }
    
}
