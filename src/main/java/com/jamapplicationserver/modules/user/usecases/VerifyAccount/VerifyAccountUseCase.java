/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyAccount;

import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public class VerifyAccountUseCase implements IUsecase<VerifyAccountRequestDTO, Result> {

    private final IUserRepository repository;
    
    @Override
    public Result execute(VerifyAccountRequestDTO request) throws GenericAppException {
                
        try {            
            
            final Result<MobileNo> mobileNoOrError =
                    MobileNo.create(request.mobileNo);
            final int code = request.code;
            
            if(mobileNoOrError.isFailure) return mobileNoOrError;
            
            final MobileNo mobileNo = mobileNoOrError.getValue();
            
            final User user = repository.fetchByMobile(mobileNo);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            final Result result = user.verify(code);
            
            if(result.isFailure) return result;
            
            repository.save(user);
            
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
