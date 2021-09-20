/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetRecommendedCollections;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;

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
            
            final Result result = usecase.execute(this.subjectId);
            
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
            this.fail(e);
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
