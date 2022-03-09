/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ActivateBlockUser;

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
public class ActivateBlockUserUseCase implements IUsecase<ActivateBlockUserRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private ActivateBlockUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(ActivateBlockUserRequestDTO request) throws GenericAppException {
                
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            final Result<UserState> stateOrError = UserState.create(request.state);
            
            final Result[] combinedProps = {
                idOrError,
                stateOrError
            };
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            final UserState state = stateOrError.getValue();
            
            final User user = repository.fetchById(id);
            
            final User updater = repository.fetchById(request.subjectId);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            if(updater == null) return Result.fail(new UpdaterUserDoesNotExistError());
            
            Result result;
            
            if(state.equals(UserState.ACTIVE))
                result = user.activateUser(updater);
            else
                result = user.blockUser(updater);
            
            if(result.isFailure) return result;

            repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static ActivateBlockUserUseCase getInstance() {
        return ActivateUserUseCaseHolder.INSTANCE;
    }
    
    private static class ActivateUserUseCaseHolder {

        private static final ActivateBlockUserUseCase INSTANCE =
                new ActivateBlockUserUseCase(UserRepository.getInstance());
    }
}
