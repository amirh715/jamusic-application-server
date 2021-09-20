/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.RemoveShowcase;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.showcase.repository.*;
import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.modules.showcase.domain.errors.*;

/**
 *
 * @author dada
 */
public class RemoveShowcaseUseCase implements IUsecase<String, String> {
    
    private final IShowcaseRepository repository;
    
    private RemoveShowcaseUseCase(IShowcaseRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Showcase showcase = this.repository.fetchById(id);
            if(showcase == null) return Result.fail(new ShowcaseDoesNotExistError());
            
            this.repository.remove(showcase);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static RemoveShowcaseUseCase getInstance() {
        return RemoveShowcaseUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveShowcaseUseCaseHolder {

        private static final RemoveShowcaseUseCase INSTANCE =
                new RemoveShowcaseUseCase(ShowcaseRepository.getInstance());
    }
}
