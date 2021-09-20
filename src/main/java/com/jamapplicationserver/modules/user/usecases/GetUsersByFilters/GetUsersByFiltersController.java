/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUsersByFilters;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import spark.QueryParamsMap;

/**
 *
 * @author amirhossein
 */
public class GetUsersByFiltersController extends BaseController {
    
    private final IUsecase<GetUsersByFiltersRequestDTO, GetUsersByFiltersResponseDTO> useCase;
    
    private GetUsersByFiltersController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("GetUsersByFiltersController");
        
        final QueryParamsMap queryParams = this.req.queryMap();
        
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
                        queryParams.get("updaterId").value()
                );
        
        try {

            final Result<GetUsersByFiltersResponseDTO> result =
                    this.useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ValidationError)
                    this.conflict(error);
                
                return;
            }
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            e.printStackTrace();
            this.fail(e.getMessage());
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
