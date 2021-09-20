/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Collections.GetCollections;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Player.Player;

/**
 *
 * @author dada
 */
public class GetCollectionsUseCase implements IUsecase<String, String> {
    
    private final ILibraryEntityRepository repository;
    private final IPlayerRepository playerRepository;
    
    private GetCollectionsUseCase() {
        this.repository = null;
        this.playerRepository = null;
    }
    
    @Override
    public Result execute(String subjectId) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(subjectId);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Player player = playerRepository.fetchById(id);
            if(player == null) return Result.ok(); // TEMPO
            
            
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetCollectionsUseCase getInstance() {
        return GetCollectionsUseCaseHolder.INSTANCE;
    }
    
    private static class GetCollectionsUseCaseHolder {

        private static final GetCollectionsUseCase INSTANCE = new GetCollectionsUseCase();
    }
}
