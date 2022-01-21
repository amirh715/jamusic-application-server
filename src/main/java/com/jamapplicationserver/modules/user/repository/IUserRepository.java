/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.repository;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.domain.MobileNo;
import com.jamapplicationserver.core.domain.Email;
import com.jamapplicationserver.core.infra.IRepository;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.exceptions.*;

/**
 *
 * @author amirhossein
 */
public interface IUserRepository extends IRepository<User> {
    
    public Set<User> fetchByFilters(UsersFilters filters);
    public User fetchByMobile(MobileNo mobile);
    public User fetchByEmail(Email email);
    public User fetchByEmailVerificationToken(UUID token);
    public void remove(User user, UniqueEntityId removerId) throws RemovingUserIsNotActiveException,
            RemovingUserIsNotAdminException, RemovingUserDoesNotExistException;
    
}
