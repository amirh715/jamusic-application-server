/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ChangePassword;

import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.IUserRepository;

/**
 *
 * @author amirhossein
 */
public class ChangePasswordUseCase implements IUsecase<ChangePasswordRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private ChangePasswordUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(ChangePasswordRequestDTO request) throws GenericAppException {
        
        System.out.println("ChangePasswordUseCase");

        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            final Result<Password> currentPasswordOrError = Password.create(request.currentPassword, false);
            final Result<Password> newPasswordOrError = Password.create(request.newPassword, false);
            
            final Result[] combinedProps = {
                idOrError,
                currentPasswordOrError,
                newPasswordOrError
            };
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            final Password currentPassword = currentPasswordOrError.getValue();
            final Password newPassword = newPasswordOrError.getValue();
            
            final User user = repository.fetchById(id);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            User updater;
            
            if(!user.id.equals(request.subjectId)) {
                updater = this.repository.fetchById(request.subjectId);
                if(updater == null)
                    return Result.fail(new UpdaterUserDoesNotExistError());
            } else
                updater = user;
            
            final Result result = user.changePassword(newPassword, currentPassword, updater);
            
            if(result.isFailure) return result;
            
            repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static ChangePasswordUseCase getInstance() {
        return ChangePasswordUseCaseHolder.INSTANCE;
    }
    
    private static class ChangePasswordUseCaseHolder {

        private static final ChangePasswordUseCase INSTANCE =
                new ChangePasswordUseCase(UserRepository.getInstance());
    }
}
