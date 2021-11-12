/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestPasswordReset;

import com.jamapplicationserver.core.domain.MobileNo;
import com.jamapplicationserver.modules.user.domain.errors.UserDoesNotExistError;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.IUserRepository;

/**
 *
 * @author amirhossein
 */
public class RequestPasswordResetUseCase implements IUsecase<RequestPasswordResetRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private RequestPasswordResetUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(RequestPasswordResetRequestDTO request) throws GenericAppException {
        
        System.out.println("RequestPasswordResetRequestUseCase");
        
        try {
            
            final Result<MobileNo> mobileOrError = MobileNo.create(request.mobile);
            
            if(mobileOrError.isFailure) return mobileOrError;
            
            final User user = repository.fetchByMobile(mobileOrError.getValue());
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            final Result result = user.requestPasswordReset();
            
            if(result.isFailure) return result;
            
            repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static RequestPasswordResetUseCase getInstance() {
        return RequestPasswordResetRequestUseCaseHolder.INSTANCE;
    }
    
    private static class RequestPasswordResetRequestUseCaseHolder {

        private static final RequestPasswordResetUseCase INSTANCE =
                new RequestPasswordResetUseCase(UserRepository.getInstance());
    }
}
