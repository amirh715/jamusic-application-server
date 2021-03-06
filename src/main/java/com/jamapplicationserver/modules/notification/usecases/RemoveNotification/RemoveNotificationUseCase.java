/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.RemoveNotification;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.RemoveNotificationRequestDTO;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.repository.*;
import com.jamapplicationserver.modules.notification.repository.exceptions.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.domain.errors.*;

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
            
            final Notification notification = repository.fetchById(id);
            if(notification == null) return Result.fail(new NotificationDoesNotExistError());
            
            repository.remove(notification);
            
            return Result.ok();
        } catch(SentNotificationCannotBeRemovedException e) {
            return Result.fail(new SentNotificationCannotBeRemovedError());
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
