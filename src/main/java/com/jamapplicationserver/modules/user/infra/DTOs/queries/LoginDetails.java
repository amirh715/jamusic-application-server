/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;

/**
 *
 * @author dada
 */
public class LoginDetails implements IQueryResponseDTO {
    
    public UUID userId;
    public String userName;
    public String userMobile;
    public String ipAddress;
    public Boolean wasSuccessful;
    public String failureReason;
    public LocalDateTime attemptedAt;
    public String platform;
    public String os;
    
    public LoginDetails(
            UUID userId,
            String userName,
            String userMobile,
            String ipAddress,
            Boolean wasSuccessful,
            String failureReason,
            LocalDateTime attemptedAt,
            String platform,
            String os
    ) {
        this.userId = userId;
        this.userName = userName;
        this.userMobile = userMobile;
        this.ipAddress = ipAddress;
        this.wasSuccessful = wasSuccessful;
        this.failureReason = failureReason;
        this.attemptedAt = attemptedAt;
        this.platform = platform;
        this.os = os;
    }
    
    @Override
    public LoginDetails filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER: return null;
            case SUBSCRIBER: return null;
            default: return null;
        }
        return this;
    }
    
}
