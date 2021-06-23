/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import com.jamapplicationserver.core.domain.*;
import java.net.Inet6Address;

/**
 *
 * @author amirhossein
 */
public class LoginAudit extends ValueObject {
    
    private Inet6Address ip;
    
    private boolean wasSuccessful;
    
    private String failureReason;
    
    private DateTime attemptedAt;
    
    private String platform;
    
    private String os;
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    
}
