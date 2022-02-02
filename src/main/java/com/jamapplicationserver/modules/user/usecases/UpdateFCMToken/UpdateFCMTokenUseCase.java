/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.UpdateFCMToken;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.modules.user.domain.errors.*;

/**
 *
 * @author dada
 */
public class UpdateFCMTokenUseCase implements IUsecase<UpdateFCMTokenRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private UpdateFCMTokenUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(UpdateFCMTokenRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<FCMToken> fcmTokenOrError = FCMToken.create(request.fcmToken);
            if(fcmTokenOrError.isFailure)
                return Result.fail(fcmTokenOrError.getError());
            final FCMToken fcmToken = fcmTokenOrError.getValue();
            
            final User user = repository.fetchById(request.subjectId);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            user.changeFCMToken(fcmToken);
            
            repository.save(user);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static UpdateFCMTokenUseCase getInstance() {
        return UpdateFCMTokenControllerHolder.INSTANCE;
    }
    
    private static class UpdateFCMTokenControllerHolder {

        private static final UpdateFCMTokenUseCase INSTANCE =
                new UpdateFCMTokenUseCase(UserRepository.getInstance());
    }
}
