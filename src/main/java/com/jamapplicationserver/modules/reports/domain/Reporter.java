/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class Reporter extends ValueObject {
    
    private final UniqueEntityId id;
    private final String name;
    private final MobileNo mobile;
    private final Email email;
    private final FCMToken fcmToken;
    
    public Reporter(
            UniqueEntityId id,
            String name,
            MobileNo mobile,
            Email email,
            FCMToken fcmToken
    ) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.fcmToken = fcmToken;
    }
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    public UniqueEntityId getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public MobileNo getMobile() {
        return this.mobile;
    }
    
    public Email getEmail() {
        return this.email;
    }
    
    public FCMToken getFCMToken() {
        return this.fcmToken;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Reporter))
            return false;
        Reporter r = (Reporter) obj;
        return r.id.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
}
