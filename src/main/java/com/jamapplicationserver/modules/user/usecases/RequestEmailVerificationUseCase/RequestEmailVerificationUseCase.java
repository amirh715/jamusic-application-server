/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestEmailVerificationUseCase;

import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;

/**
 *
 * @author amirhossein
 */
public class RequestEmailVerificationUseCase implements IUseCase<UniqueEntityID, Result> {
    
    private final IUserRepository repository;
    
    private RequestEmailVerificationUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(UniqueEntityID id) throws GenericAppException {
        
        System.out.println("RequestEmailVerificationUseCase");
        
        try {
            
            final User user = this.repository.fetchById(id);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            final Result result = user.requestEmailVerification();
            
            if(result.isFailure) return result;
            
            this.repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException();
        }
        
    }
    
    public static RequestEmailVerificationUseCase getInstance() {
        return RequestEmailVerificationUseCaseHolder.INSTANCE;
    }
    
    private static class RequestEmailVerificationUseCaseHolder {

        private static final RequestEmailVerificationUseCase INSTANCE =
                new RequestEmailVerificationUseCase(UserRepository.getInstance());
    }
}
