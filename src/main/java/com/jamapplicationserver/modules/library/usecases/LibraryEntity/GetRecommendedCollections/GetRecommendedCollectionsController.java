/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetRecommendedCollections;

import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;
import java.time.Duration;

/**
 *
 * @author dada
 */
public class GetRecommendedCollectionsController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetRecommendedCollectionsController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Result<Set<RecommendedCollection<LibraryEntityDetails>>> result = usecase.execute(subjectId);
            
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
            
            if(subjectRole.isSubscriber()) {
                privateCache();
                cache(Duration.ofHours(2));
                staleWhileRevalidate(Duration.ofDays(4));
                staleIfError(Duration.ofDays(4));
            }
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetRecommendedCollectionsController getInstance() {
        return GetRecommControllerHolder.INSTANCE;
    }
    
    private static class GetRecommControllerHolder {

        private static final GetRecommendedCollectionsController INSTANCE =
                new GetRecommendedCollectionsController(GetRecommendedCollectionsUseCase.getInstance());
    }
}
