/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ResetPassword;

import com.jamapplicationserver.core.domain.MobileNo;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.IUserRepository;
import com.jamapplicationserver.modules.user.repository.UserRepository;

/**
 *
 * @author amirhossein
 */
public class ResetPasswordUseCase implements IUsecase<ResetPasswordRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private ResetPasswordUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(ResetPasswordRequestDTO request) throws GenericAppException {
        
        System.out.println("ResetPasswordUseCase");
        
        try {
            
            final Result<MobileNo> mobileOrError = MobileNo.create(request.mobile);
            final int resetCode = Integer.valueOf(request.resetCode);
            final Result<Password> newPasswordOrError = Password.create(request.newPassword, false);
            
            final Result[] combinedProps = {
                mobileOrError,
                newPasswordOrError
            };
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final MobileNo mobile = mobileOrError.getValue();
            final Password newPassword = newPasswordOrError.getValue();
            
            final User user = this.repository.fetchByMobile(mobile);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            final Result result = user.resetPassword(newPassword, resetCode);
            
            if(result.isFailure) return result;
            
            this.repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static ResetPasswordUseCase getInstance() {
        return ResetPasswordUseCaseHolder.INSTANCE;
    }
    
    private static class ResetPasswordUseCaseHolder {

        private static final ResetPasswordUseCase INSTANCE =
                new ResetPasswordUseCase(UserRepository.getInstance());
    }
}
