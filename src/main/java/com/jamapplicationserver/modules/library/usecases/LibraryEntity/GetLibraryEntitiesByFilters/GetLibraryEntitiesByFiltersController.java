/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntitiesByFilters;

import java.util.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.GetLibraryEntitiesByFiltersRequestDTO;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import spark.QueryParamsMap;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetLibraryEntitiesByFiltersController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final QueryParamsMap fields = this.req.queryMap();
                        
            final GetLibraryEntitiesByFiltersRequestDTO dto =
                    new GetLibraryEntitiesByFiltersRequestDTO(
                            fields.get("searchTerm").value(),
                            fields.get("type").value(),
                            fields.get("genreIds").value(),
                            fields.get("rateFrom").value(),
                            fields.get("rateTo").value(),
                            fields.get("tags").value(),
                            fields.get("isFlagged").value() != null ?
                                    Boolean.valueOf(fields.get("isFlagged").value()) : null,
                            fields.get("published").value() != null ?
                                    Boolean.valueOf(fields.get("published").value()) : null,
                            fields.get("hasImage").value() != null ?
                                    Boolean.valueOf(fields.get("hasImage").value()) : null,
                            fields.get("creatorId").value(),
                            fields.get("updaterId").value(),
                            fields.get("createdAtFrom").value(),
                            fields.get("createdAtTill").value(),
                            fields.get("lastModifiedAtFrom").value(),
                            fields.get("lastModifiedAtTill").value()
                    );
                        
            final Result result = this.usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                if(error instanceof ConflictError)
                    this.conflict(error);
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            e.printStackTrace();
            this.fail(e);
        }
        
    }
    
    public static GetLibraryEntitiesByFiltersController getInstance() {
        return GetLibraryEntitiesByFiltersControllerHolder.INSTANCE;
    }
    
    private static class GetLibraryEntitiesByFiltersControllerHolder {

        private static final GetLibraryEntitiesByFiltersController INSTANCE =
                new GetLibraryEntitiesByFiltersController(GetLibraryEntitiesByFiltersUseCase.getInstance());
    }
}
