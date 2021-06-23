/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyAccount;

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
public class VerifyAccountUseCase implements IUseCase<VerifyAccountRequestDTO, Result> {

    private final IUserRepository repository;
    
    @Override
    public Result execute(VerifyAccountRequestDTO request) throws GenericAppException {
        
        System.out.println("VerifyAccountUseCase");
        
        try {            
            
            final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromString(request.id);
            final int code = request.code;
            
            if(idOrError.isFailure) return idOrError;
            
            final User user = this.repository.fetchById(idOrError.getValue());
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            final Result result = user.verify(code);
            
            if(result.isFailure) return result;
            
            this.repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException();
        }
        
    }
    
    private VerifyAccountUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    public static VerifyAccountUseCase getInstance() {
        return VerifyAccountUseCaseHolder.INSTANCE;
    }
    
    private static class VerifyAccountUseCaseHolder {

        private static final VerifyAccountUseCase INSTANCE =
                new VerifyAccountUseCase(UserRepository.getInstance());
    }
}
