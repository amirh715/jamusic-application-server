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
public class RemoveUnverifiedEmailsJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        System.out.println("JOB : " + this.getClass().getSimpleName());
        
        final IUserRepository repository = UserRepository.getInstance();
        
        // users with emails
        final UsersFilters unverifiedEmails = null;
        
        // fetch users
        final Set<User> users =
                repository.fetchByFilters(unverifiedEmails)
                .stream()
                .filter(user -> !user.hasEmail() && !user.isEmailVerified())
                .collect(Collectors.toSet());
        if(users.isEmpty()) return;
        
//        users.forEach(user -> {
//            user.removeEmail();
//            repository.save(user);
//        });
        
    }
    
}
