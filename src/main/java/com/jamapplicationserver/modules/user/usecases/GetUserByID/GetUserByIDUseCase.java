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
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.services.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;

/**
 *
 * @author amirhossein
 */
public class GetUserByIDUseCase implements IUsecase<String, User> {
    
    private final IUserQueryService queryService;
    
    private GetUserByIDUseCase(IUserQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<User> execute(String userID) throws GenericAppException {
                
        try {
            
            final Result<UniqueEntityId> userIdOrError = UniqueEntityId.createFromString(userID);
            
            if(userIdOrError.isFailure)
                return Result.fail(userIdOrError.getError());
            
            final UniqueEntityId id = userIdOrError.getValue();
            
            final UserDetails user = queryService.getUserById(id);
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
                new GetUserByIDUseCase(UserQueryService.getInstance());
    }
}
