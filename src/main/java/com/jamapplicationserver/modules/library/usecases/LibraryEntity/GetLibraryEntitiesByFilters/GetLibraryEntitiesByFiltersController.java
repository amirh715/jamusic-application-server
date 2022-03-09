/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntitiesByFilters;

import java.util.*;
import spark.QueryParamsMap;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityDetails;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.GetLibraryEntitiesByFiltersRequestDTO;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import java.time.Duration;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersController extends BaseController {
    
    private final IUsecase<GetLibraryEntitiesByFiltersRequestDTO, Set<LibraryEntityDetails>> usecase;
    
    private GetLibraryEntitiesByFiltersController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            System.out.println("GetLibraryEntitiesByFiltersController");
            
            final QueryParamsMap fields = req.queryMap();
                        
            final GetLibraryEntitiesByFiltersRequestDTO dto =
                    new GetLibraryEntitiesByFiltersRequestDTO(
                            fields.get("type").value(),
                            fields.get("searchTerm").value(),
                            fields.get("published").value(),
                            fields.get("isFlagged").value(),
                            fields.get("hasImage").value(),
                            fields.get("genreIds").value(),
                            fields.get("rateFrom").value(),
                            fields.get("rateTill").value(),
                            fields.get("totalPlayedCountFrom").value(),
                            fields.get("totalPlayedCountTo").value(),
                            fields.get("durationFrom").value(),
                            fields.get("durationTo").value(),
                            fields.get("artistId").value(),
                            fields.get("releaseDateFrom").value(),
                            fields.get("releaseDateTill").value(),
                            fields.get("creatorId").value(),
                            fields.get("updaterId").value(),
                            fields.get("createdAtFrom").value(),
                            fields.get("createdAtTill").value(),
                            fields.get("lastModifiedAtFrom").value(),
                            fields.get("lastModifiedAtTill").value(),
                            subjectId,
                            subjectRole,
                            fields.get("limit").integerValue(),
                            fields.get("offset").integerValue()
                    );
                        
            final Result<Set<LibraryEntityDetails>> result = usecase.execute(dto);
            
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
            
            if(subjectRole.isSubscriber()) {
                publicCache();
                cache(Duration.ofHours(1));
                staleWhileRevalidate(Duration.ofDays(1));
                staleIfError(Duration.ofDays(1));
            }
            ok(result.getValue());
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
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
