/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.repository;

import com.jamapplicationserver.core.domain.MobileNo;
import com.jamapplicationserver.core.domain.Email;
import java.util.Set;
import java.net.URL;
import com.jamapplicationserver.core.infra.IRepository;
import com.jamapplicationserver.modules.user.domain.*;
/**
 *
 * @author amirhossein
 */
public interface IUserRepository extends IRepository<User> {
    
    public Set<User> fetchByFilters(UsersFilters filters);
    public User fetchByMobile(MobileNo mobile);
    public User fetchByEmail(Email email);
    public User fetchByEmailVerificationLink(URL link);
    
}
