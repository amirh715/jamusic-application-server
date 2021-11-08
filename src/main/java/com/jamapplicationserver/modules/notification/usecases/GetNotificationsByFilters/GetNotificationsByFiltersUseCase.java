/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationsByFilters;

import java.util.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.GetNotificationsByFiltersRequestDTO;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.notification.infra.services.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.repository.NotificationFilters;

/**
 *
 * @author dada
 */
public class GetNotificationsByFiltersUseCase
        implements IUsecase<GetNotificationsByFiltersRequestDTO, Set<NotificationDetails>> {
    
    private final INotificationQueryService queryService;
    
    private GetNotificationsByFiltersUseCase(INotificationQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<Set<NotificationDetails>> execute(GetNotificationsByFiltersRequestDTO request) {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<DateTime> createdAtFromOrError =
                    DateTime.create(request.createdAtFrom);
            final Result<DateTime> createdAtTillOrError =
                    DateTime.create(request.createdAtTill);
            final Result<DateTime> lastModifiedAtFromOrError =
                    DateTime.create(request.lastModifiedAtFrom);
            final Result<DateTime> lastModifiedAtTillOrError =
                    DateTime.create(request.lastModifiedAtTill);
            final Result<DateTime> scheduledOnFromOrError =
                    DateTime.create(request.scheduledOnFrom);
            final Result<DateTime> scheduledOnTillOrError =
                    DateTime.create(request.scheduledOnTill);
            final Boolean isSent = request.isSent != null ?
                    Boolean.parseBoolean(request.isSent) : null;
            final Boolean withUndeliveredRecipients = request.withUndeliveredRecipients != null ?
                    Boolean.parseBoolean(request.withUndeliveredRecipients) : null;
            final Result<SenderType> senderTypeOrError = SenderType.create(request.senderType);
            final Result<NotifType> typeOrError = NotifType.create(request.type);
            
            if(request.createdAtFrom != null)
                combinedProps.add(createdAtFromOrError);
            if(request.createdAtTill != null)
                combinedProps.add(createdAtTillOrError);
            if(request.lastModifiedAtFrom != null)
                combinedProps.add(lastModifiedAtFromOrError);
            if(request.lastModifiedAtTill != null)
                combinedProps.add(lastModifiedAtTillOrError);
            if(request.scheduledOnFrom != null)
                combinedProps.add(scheduledOnFromOrError);
            if(request.scheduledOnTill != null)
                combinedProps.add(scheduledOnTillOrError);
            if(request.senderType != null)
                combinedProps.add(senderTypeOrError);
            if(request.type != null)
                combinedProps.add(typeOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return Result.fail(combinedPropsResult.getError());
            
            final DateTime createdAtFrom =
                    request.createdAtFrom != null ? createdAtFromOrError.getValue() : null;
            final DateTime createdAtTill =
                    request.createdAtTill != null ? createdAtTillOrError.getValue() : null;
            final DateTime lastModifiedAtFrom =
                    request.lastModifiedAtFrom != null ? lastModifiedAtFromOrError.getValue() : null;
            final DateTime lastModifiedAtTill =
                    request.lastModifiedAtTill != null ? lastModifiedAtTillOrError.getValue() : null;
            final DateTime scheduledOnFrom =
                    request.scheduledOnFrom != null ? scheduledOnFromOrError.getValue() : null;
            final DateTime scheduledOnTill =
                    request.scheduledOnTill != null ? scheduledOnTillOrError.getValue() : null;
            final SenderType senderType =
                    request.senderType != null ? senderTypeOrError.getValue() : null;
            final NotifType type =
                    request.type != null ? typeOrError.getValue() : null;
            
            final NotificationFilters filters =
                    new NotificationFilters(
                            createdAtFrom,
                            createdAtTill,
                            lastModifiedAtFrom,
                            lastModifiedAtTill,
                            scheduledOnFrom,
                            scheduledOnTill,
                            request.searchTerm,
                            isSent,
                            withUndeliveredRecipients,
                            type,
                            senderType
                    );
            
            return Result.ok(queryService.getNotificationsByFilters(filters));
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    public static GetNotificationsByFiltersUseCase getInstance() {
        return GetNotificationsByFiltersUseCaseHolder.INSTANCE;
    }
    
    private static class GetNotificationsByFiltersUseCaseHolder {

        private static final GetNotificationsByFiltersUseCase INSTANCE =
                new GetNotificationsByFiltersUseCase(NotificationQueryService.getInstance());
    }
}
