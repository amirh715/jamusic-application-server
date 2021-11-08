/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationById;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.infra.services.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;

/**
 *
 * @author dada
 */
public class GetNotificationByIdUseCase implements IUsecase<String, NotificationDetails> {
    
    private final INotificationQueryService queryService;
    
    private GetNotificationByIdUseCase(INotificationQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<NotificationDetails> execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
            
            return Result.ok(queryService.getNotificationById(id));
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetNotificationByIdUseCase getInstance() {
        return GetNotificationByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetNotificationByIdUseCaseHolder {

        private static final GetNotificationByIdUseCase INSTANCE =
                new GetNotificationByIdUseCase(NotificationQueryService.getInstance());
    }
}
