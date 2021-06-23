/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyEmail;

import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import java.nio.file.*;
import java.net.URL;

/**
 *
 * @author amirhossein
 */
public class VerifyEmailUseCase implements IUseCase<VerifyEmailRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private VerifyEmailUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(VerifyEmailRequestDTO request) throws GenericAppException {
        
        System.out.println("VerifyEmailUseCase");
        
        try {
            
            final URL link =
                    new URL(request.link);
            
            final User user = this.repository.fetchByEmailVerificationLink(link);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            final Result result = user.verifyEmail(link);
            
            if(result.isFailure) return result;
            
            this.repository.save(user);

            return Result.ok();
            
        } catch(InvalidPathException e) {
            return Result.fail(new ValidationError("link is invalid"));
        } catch(Exception e) {
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
