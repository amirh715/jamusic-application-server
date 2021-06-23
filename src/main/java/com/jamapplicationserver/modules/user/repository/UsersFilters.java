/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.repository;

import com.jamapplicationserver.infra.Persistence.database.Models.UserStateEnum;
import com.jamapplicationserver.infra.Persistence.database.Models.UserRoleEnum;
import java.time.*;
import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.user.domain.*;

/**
 *
 * @author amirhossein
 */
public class UsersFilters {
    
    public final String searchTerm;
    public final LocalDateTime createdAtFrom;
    public final LocalDateTime createdAtTill;
    public final LocalDateTime lastModifiedAtFrom;
    public final LocalDateTime lastModifiedAtTill;
    public final Boolean hasImage;
    public final Boolean hasEmail;
    public final UserStateEnum state;
    public final UserRoleEnum role;
    public final UUID creatorId;
    public final UUID updaterId;
    
    public UsersFilters(
           String searchTerm,
           DateTime createdAtFrom,
           DateTime createdAtTill,
           DateTime lastModifiedAtFrom,
           DateTime lastModifiedAtTill,
           String hasImage,
           String hasEmail,
           UserState state,
           UserRole role,
           UUID creatorId,
           UUID updaterId
    ) {
        this.searchTerm = searchTerm;
        this.createdAtFrom = createdAtFrom != null ? createdAtFrom.getValue() : null;
        this.createdAtTill = createdAtTill != null ? createdAtTill.getValue() : null;
        this.lastModifiedAtFrom = lastModifiedAtFrom != null ? lastModifiedAtFrom.getValue() : null;
        this.lastModifiedAtTill = lastModifiedAtTill != null ? lastModifiedAtTill.getValue() : null;
        this.hasImage = hasImage != null ? Boolean.parseBoolean(hasImage) : null;
        this.hasEmail = hasEmail != null ? Boolean.parseBoolean(hasEmail) : null;
        this.state = state != null ? UserStateEnum.valueOf(state.getValue()) : null;
        this.role = role != null ? UserRoleEnum.valueOf(role.getValue()) : null;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
    }
    
}
