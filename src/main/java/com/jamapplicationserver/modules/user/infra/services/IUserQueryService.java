/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.services;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.user.repository.UsersFilters;

/**
 *
 * @author dada
 */
public interface IUserQueryService {
    
    public Set<UserDetails> getUsersByFilters(UsersFilters filters);
    
    public UserDetails getUserById(UniqueEntityId id);
    
    public Set<LoginDetails> getLoginsOfUser(UniqueEntityId id);
    
    public Set<LoginDetails> getAllLogins(Integer limit, Integer offset);
    
    public UsersSummary getUsersSummary();
    
}
