/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.RemoveNotification;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.RemoveNotificationRequestDTO;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.repository.*;
import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author dada
 */
public class RemoveNotificationUseCase implements IUsecase<RemoveNotificationRequestDTO, String> {
    
    private final INotificationRepository repository;
    
    private RemoveNotificationUseCase(INotificationRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(RemoveNotificationRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Notification notification = this.repository.fetchById(id);
            
            this.repository.remove(notification);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static RemoveNotificationUseCase getInstance() {
        return RemoveNotificationUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveNotificationUseCaseHolder {

        private static final RemoveNotificationUseCase INSTANCE =
                new RemoveNotificationUseCase(NotificationRepository.getInstance());
    }
}
