/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.ShowcaseInteractedWith;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.showcase.repository.*;
import com.jamapplicationserver.modules.showcase.domain.Showcase;

/**
 *
 * @author dada
 */
public class ShowcaseInteractedWithUseCase implements IUsecase<String, String> {
    
    private final IShowcaseRepository repository;
    
    private ShowcaseInteractedWithUseCase(IShowcaseRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Showcase showcase = this.repository.fetchById(id);
            
            showcase.interactedWith();
            
            this.repository.save(showcase);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static ShowcaseInteractedWithUseCase getInstance() {
        return ShowcaseInteractedWithUseCaseHolder.INSTANCE;
    }
    
    private static class ShowcaseInteractedWithUseCaseHolder {

        private static final ShowcaseInteractedWithUseCase INSTANCE =
                new ShowcaseInteractedWithUseCase(ShowcaseRepository.getInstance());
    }
}
