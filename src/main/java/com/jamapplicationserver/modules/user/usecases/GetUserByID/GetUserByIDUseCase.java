/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserByID;

import com.jamapplicationserver.modules.user.domain.errors.UserDoesNotExistError;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.user.repository.IUserRepository;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class GetUserByIDUseCase implements IUsecase<String, User> {
    
    private final IUserRepository repository;
    
    private GetUserByIDUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result<User> execute(String userID) throws GenericAppException {
        
        System.out.println("GetUserByIdUseCase");
        
        try {
            
            final Result<UniqueEntityId> userIdOrError = UniqueEntityId.createFromString(userID);
            
            if(userIdOrError.isFailure)
                return Result.fail(userIdOrError.getError());
            
            final UniqueEntityId id = userIdOrError.getValue();
            
            final User user = repository.fetchById(id);

            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            return Result.ok(user);
            
        } catch(Exception e) {
            throw new GenericAppException();
        }
        
    }
    
    public static GetUserByIDUseCase getInstance() {
        return GetUserByIDUseCaseHolder.INSTANCE;
    }
    
    private static class GetUserByIDUseCaseHolder {

        private static final GetUserByIDUseCase INSTANCE =
                new GetUserByIDUseCase(UserRepository.getInstance());
    }
}
