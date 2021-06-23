/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestAccountVerificationCode;

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
public class RequestAccountVerificationCodeUseCase implements IUseCase<UniqueEntityID, Result> {
    
    private final IUserRepository repository;
    
    @Override
    public Result execute(UniqueEntityID id) throws GenericAppException {
        
        System.out.println("RequestAccountVerificationCodeUseCase");

        try {
            
            final User user = this.repository.fetchById(id);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
        
            final Result result = user.requestUserVerification();

            if(result.isFailure) return result;
            
            this.repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
    }
    
    private RequestAccountVerificationCodeUseCase(IUserRepository respository) {
        this.repository = respository;
    }
    
    public static RequestAccountVerificationCodeUseCase getInstance() {
        return RequestAccountVerificationCodeUseCaseHolder.INSTANCE;
    }
    
    private static class RequestAccountVerificationCodeUseCaseHolder {

        private static final RequestAccountVerificationCodeUseCase INSTANCE =
                new RequestAccountVerificationCodeUseCase(UserRepository.getInstance());
    }
}
