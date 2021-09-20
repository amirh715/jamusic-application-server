/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.Jobs;

import org.quartz.*;
import java.util.*;
import java.util.stream.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.*;

/**
 *
 * @author dada
 */
public class RemoveUnverifiedUsersJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        System.out.println("JOB : " + this.getClass().getSimpleName());
        
        final IUserRepository repository = UserRepository.getInstance();
        
        // unverified users
        final UsersFilters unverifiedUsers = null;
        
        // fetch users
        final Set<User> users =
                repository.fetchByFilters(unverifiedUsers);
        if(users.isEmpty()) return;
        
        users.forEach(user -> repository.remove(user));
        
    }
    
}
