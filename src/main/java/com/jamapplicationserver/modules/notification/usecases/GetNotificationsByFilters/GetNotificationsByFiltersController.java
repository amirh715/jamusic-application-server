/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationsByFilters;

import java.util.*;
import spark.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.GetNotificationsByFiltersRequestDTO;

/**
 *
 * @author dada
 */
public class GetNotificationsByFiltersController extends BaseController {
    
    private final IUsecase<GetNotificationsByFiltersRequestDTO, Set<NotificationDetails>> usecase;
    
    private GetNotificationsByFiltersController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final QueryParamsMap fields = req.queryMap();

            final GetNotificationsByFiltersRequestDTO dto =
                    new GetNotificationsByFiltersRequestDTO(
                            fields.get("searchTerm").value(),
                            fields.get("createdAtFrom").value(),
                            fields.get("createdAtTill").value(),
                            fields.get("lastModifiedAtFrom").value(),
                            fields.get("lastModifiedAtTill").value(),
                            fields.get("scheduledOnFrom").value(),
                            fields.get("scheduledOnTill").value(),
                            fields.get("type").value(),
                            fields.get("isSent").value(),
                            fields.get("withUndeliveredRecipients").value(),
                            fields.get("senderType").value(),
                            subjectId,
                            subjectRole
                    );
            
            final Result<Set<NotificationDetails>> result = usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                if(error instanceof ConflictError)
                    conflict(error);
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                return;
            }
            
            noStore();
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetNotificationsByFiltersController getInstance() {
        return GetNotificationsByFiltersControllerHolder.INSTANCE;
    }
    
    private static class GetNotificationsByFiltersControllerHolder {

        private static final GetNotificationsByFiltersController INSTANCE =
                new GetNotificationsByFiltersController(GetNotificationsByFiltersUseCase.getInstance());
    }
}
