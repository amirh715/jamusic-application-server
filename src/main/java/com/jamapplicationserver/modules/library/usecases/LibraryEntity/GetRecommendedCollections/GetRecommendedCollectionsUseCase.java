/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetRecommendedCollections;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.RecommendedCollection;
import com.jamapplicationserver.modules.library.infra.services.*;

/**
 *
 * @author dada
 */
public class GetRecommendedCollectionsUseCase implements IUsecase<String, Set<RecommendedCollection>> {
    
    private final IRecommendationService service;
    
    private GetRecommendedCollectionsUseCase(IRecommendationService service) {
        this.service = service;
    }
    
    @Override
    public Result execute(String playerId) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> playerIdOrError = UniqueEntityId.createFromString(playerId);
            if(playerIdOrError.isFailure) return playerIdOrError;
            
            return Result.ok(service.getCollections(playerIdOrError.getValue()));
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetRecommendedCollectionsUseCase getInstance() {
        return GetRecommUseCaseHolder.INSTANCE;
    }
    
    private static class GetRecommUseCaseHolder {

        private static final GetRecommendedCollectionsUseCase INSTANCE =
                new GetRecommendedCollectionsUseCase(RecommendationService.getInstance());
    }
}
