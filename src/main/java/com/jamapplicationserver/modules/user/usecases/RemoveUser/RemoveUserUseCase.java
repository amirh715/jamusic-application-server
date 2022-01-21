/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RemoveUser;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.IUserRepository;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.modules.user.repository.exceptions.*;

/**
 *
 * @author amirhossein
 */
public class RemoveUserUseCase implements IUsecase<RemoveUserRequestDTO, Object> {
    
    private final IUserRepository repository;
    
    private RemoveUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(RemoveUserRequestDTO request) throws GenericAppException {
        
        System.out.println("RemoveUserUseCase");
        
        try {
            
            final Result<UniqueEntityId> idOrError =
                    UniqueEntityId.createFromString(request.id);
            
            final Result[] combinedProps = {
                idOrError
            };
            
            final Result combinedPropsResult =
                    Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final User user = repository.fetchById(idOrError.getValue());
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            repository.remove(user, request.subjectId);
            
            return Result.ok();
            
        } catch(RemovingUserIsNotAdminException e) {
            return Result.fail(new UserIsNotAdminError(e.getMessage()));
        } catch(RemovingUserIsNotActiveException e) {
            return Result.fail(new UserIsNotActiveError(e.getMessage()));
        } catch(RemovingUserDoesNotExistException e) {
            return Result.fail(new UserDoesNotExistError());
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static RemoveUserUseCase getInstance() {
        return RemoveUserUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveUserUseCaseHolder {

        private static final RemoveUserUseCase INSTANCE =
                new RemoveUserUseCase(UserRepository.getInstance());
    }
}
