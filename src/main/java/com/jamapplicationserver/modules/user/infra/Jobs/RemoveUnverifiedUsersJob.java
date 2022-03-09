/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.Jobs;

import org.quartz.*;
import java.util.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.*;

/**
 *
 * @author dada
 */
public class RemoveUnverifiedUsersJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        try {
                    
            final IUserRepository repository = UserRepository.getInstance();

            // unverified users
            final UsersFilters unverifiedUsers = null;

            // fetch users
            final Set<User> users =
                    repository.fetchByFilters(unverifiedUsers);
            if(users.isEmpty()) return;
            
            for(User user : users)
                repository.remove(user);
            
        } catch(Exception e) {
            // LOG
        }
        
    }
    
}
