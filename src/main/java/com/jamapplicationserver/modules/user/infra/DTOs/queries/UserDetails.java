/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.DTOs.queries;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class UserDetails implements IQueryResponseDTO {
    
    public UUID id;
    public String name;
    public String mobile;
    public String email;
    public Boolean isEmailVerified;
    public UserStateEnum state;
    public UserRoleEnum role;
    public LocalDateTime createdAt;
    public LocalDateTime lastModifiedAt;
    public UUID creatorId;
    public String creatorName;
    public UUID updaterId;
    public String updaterName;
    public Long totalReportsCount;
    public Long totalNotificationsCount;
    public Long totalLoginCount;
    public Long totalPlayedTracksCount;
    
    public UserDetails(
            UUID id,
            String name,
            String mobile,
            String email,
            Boolean isEmailVerified,
            UserStateEnum state,
            UserRoleEnum role,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            UUID creatorId,
            String creatorName,
            UUID updaterId,
            String updaterName,
            Long totalReportsCount,
            Long totalNotificationsCount,
            Long totalLoginCount,
            Long totalPlayedTracksCount
    ) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.state = state;
        this.role = role;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.updaterId = updaterId;
        this.updaterName = updaterName;
        this.totalReportsCount = totalReportsCount;
        this.totalNotificationsCount = totalNotificationsCount;
        this.totalLoginCount = totalLoginCount;
        this.totalPlayedTracksCount = totalPlayedTracksCount;
    }
    
    @Override
    public UserDetails filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER:
                this.state = null;
                this.role = null;
                this.createdAt = null;
                this.lastModifiedAt = null;
                this.creatorId = null;
                this.creatorName = null;
                this.updaterId = null;
                this.updaterName = null;
                this.totalLoginCount = null;
                this.totalNotificationsCount = null;
                this.totalPlayedTracksCount = null;
                this.totalReportsCount = null;
                break;
            case SUBSCRIBER:
                this.state = null;
                this.role = null;
                this.createdAt = null;
                this.lastModifiedAt = null;
                this.creatorId = null;
                this.creatorName = null;
                this.updaterId = null;
                this.updaterName = null;
                this.totalLoginCount = null;
                this.totalNotificationsCount = null;
                this.totalPlayedTracksCount = null;
                this.totalReportsCount = null;
                break;
            default:
                return null;
        }
        return this;
    }
    
}
