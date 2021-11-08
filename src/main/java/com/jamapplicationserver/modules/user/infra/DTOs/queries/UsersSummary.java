/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class UsersSummary implements IQueryResponseDTO {
    
    public Long totalUsersCount;
    public Long activeUsersCount;
    public Long blockedUsersCount;
    public Long unverifiedUsersCount;
    public Long usersWithEmailsCount;
    public Long usersWithVerifiedEmailsCount;
    public Set<LoginDetails> unsuccessfulLogins;
    public Set<LoginDetails> successfulLogins;
    public Long unsuccessfulLoginsCount;
    public Long successfulLoginsCount;

    public UsersSummary(
            long totalUsersCount,
            long activeUsersCount,
            long blockedUsersCount,
            long unverifiedUsersCount,
            long usersWithEmailsCount,
            long usersWithVerifiedEmailsCount,
            Set<LoginDetails> unsuccessfulLogins,
            Set<LoginDetails> successfulLogins,
            long unsuccessfulLoginsCount,
            long successfulLoginsCount
    ) {
        this.totalUsersCount = totalUsersCount;
        this.activeUsersCount = activeUsersCount;
        this.blockedUsersCount = blockedUsersCount;
        this.unverifiedUsersCount = unverifiedUsersCount;
        this.usersWithEmailsCount = usersWithEmailsCount;
        this.usersWithVerifiedEmailsCount = usersWithVerifiedEmailsCount;
        this.unsuccessfulLogins = unsuccessfulLogins;
        this.successfulLogins = successfulLogins;
        this.unsuccessfulLoginsCount = unsuccessfulLoginsCount;
        this.successfulLoginsCount = successfulLoginsCount;
    }
    
    public UsersSummary() {
        
    }
    
    @Override
    public UsersSummary filter(UserRole role) {
        
        
        return this;
    }
    
}
