/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetShowcaseById;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.modules.showcase.repository.*;

/**
 *
 * @author dada
 */
public class GetShowcaseByIdUseCase implements IUsecase<String, Showcase> {
    
    private final IShowcaseRepository repository;
    
    private GetShowcaseByIdUseCase(IShowcaseRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Showcase showcase = this.repository.fetchById(id);
            
            return Result.ok(showcase);
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetShowcaseByIdUseCase getInstance() {
        return GetShowcaseByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetShowcaseByIdUseCaseHolder {

        private static final GetShowcaseByIdUseCase INSTANCE =
                new GetShowcaseByIdUseCase(ShowcaseRepository.getInstance());
    }
}
