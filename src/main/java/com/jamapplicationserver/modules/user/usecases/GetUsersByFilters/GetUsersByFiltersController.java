/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUsersByFilters;

import com.jamapplicationserver.modules.user.infra.DTOs.commands.GetUsersByFiltersRequestDTO;
import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.UserDetails;
import spark.QueryParamsMap;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class GetUsersByFiltersController extends BaseController {
    
    private final IUsecase<GetUsersByFiltersRequestDTO, Set<UserDetails>> useCase;
    
    private GetUsersByFiltersController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
                
        final QueryParamsMap queryParams = req.queryMap();
        
        final GetUsersByFiltersRequestDTO dto =
                new GetUsersByFiltersRequestDTO(
                        queryParams.get("searchTerm").value(),
                        queryParams.get("createdAtFrom").value(),
                        queryParams.get("createdAtTill").value(),
                        queryParams.get("lastModifiedAtFrom").value(),
                        queryParams.get("lastModifiedAtTill").value(),
                        queryParams.get("hasImage").value(),
                        queryParams.get("hasEmail").value(),
                        queryParams.get("state").value(),
                        queryParams.get("role").value(),
                        queryParams.get("creatorId").value(),
                        queryParams.get("updaterId").value(),
                        queryParams.get("limit").integerValue(),
                        queryParams.get("offset").integerValue()
                );
        
        try {

            final Result<Set<UserDetails>> result = useCase.execute(dto);
            
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
            LogService.getInstance().error(e);
            fail(e.getMessage());
        }
        
    }
    
    public static GetUsersByFiltersController getInstance() {
        return GetUsersByFiltersControllerHolder.INSTANCE;
    }
    
    private static class GetUsersByFiltersControllerHolder {

        private static final GetUsersByFiltersController INSTANCE =
                new GetUsersByFiltersController(GetUsersByFiltersUseCase.getInstance());
    }
}
