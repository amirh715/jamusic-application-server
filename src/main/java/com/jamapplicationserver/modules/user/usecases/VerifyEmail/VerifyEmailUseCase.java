/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyEmail;

import java.util.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class VerifyEmailUseCase implements IUsecase<VerifyEmailRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private VerifyEmailUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(VerifyEmailRequestDTO request) throws GenericAppException {
                
        try {

            if(request.token == null)
                return Result.fail(new ValidationError("Email verification token is required"));
                
            final UUID token = UUID.fromString(request.token);
            
            final User user = repository.fetchByEmailVerificationToken(token);
            
            if(user == null) return Result.fail(new EmailVerificationLinkDoesNotExistError());
            
            final Result result = user.verifyEmail(token);
            
            if(result.isFailure) return result;
            
            repository.save(user);

            return Result.ok();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
        
    }
    
    public static VerifyEmailUseCase getInstance() {
        return VerifyEmailUseCaseHolder.INSTANCE;
    }
    
    private static class VerifyEmailUseCaseHolder {

        private static final VerifyEmailUseCase INSTANCE =
                new VerifyEmailUseCase(UserRepository.getInstance());
    }
}
