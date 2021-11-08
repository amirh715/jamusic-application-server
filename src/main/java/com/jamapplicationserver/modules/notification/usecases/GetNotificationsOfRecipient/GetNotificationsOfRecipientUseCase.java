/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationsOfRecipient;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.infra.services.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.*;

/**
 *
 * @author dada
 */
public class GetNotificationsOfRecipientUseCase
        implements IUsecase<GetNotificationsOfRecipientRequestDTO, RecipientDetails> {
    
    private INotificationQueryService queryService;
    
    private GetNotificationsOfRecipientUseCase(INotificationQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<RecipientDetails> execute(GetNotificationsOfRecipientRequestDTO request)
            throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> recipientIdOrError =
                    UniqueEntityId.createFromString(request.recipientId);
            if(recipientIdOrError.isFailure) return Result.fail(recipientIdOrError.getError());
            
            final UniqueEntityId recipientId = recipientIdOrError.getValue();
            
            return Result.ok(queryService.getNotificationsOfRecipient(recipientId));
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetNotificationsOfRecipientUseCase getInstance() {
        return GetNotificationsOfRecipientUseCaseHolder.INSTANCE;
    }
    
    private static class GetNotificationsOfRecipientUseCaseHolder {

        private static final GetNotificationsOfRecipientUseCase INSTANCE =
                new GetNotificationsOfRecipientUseCase(NotificationQueryService.getInstance());
    }
}
