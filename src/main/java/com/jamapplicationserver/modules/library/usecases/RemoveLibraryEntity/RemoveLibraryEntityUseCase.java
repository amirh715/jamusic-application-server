/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.RemoveLibraryEntity;

import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class RemoveLibraryEntityUseCase implements IUseCase<String, String> {
    
    private final ILibraryEntityRepository repository;
    
    private RemoveLibraryEntityUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromString(idString);
            
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityID id = idOrError.getValue();
            
            final LibraryEntity entity =
                    this.repository.fetchById(id)
                            .includeUnpublished()
                            .getSingleResult();
            
            if(entity == null) return Result.fail(new LibraryEntityDoesNotExistError());
            
            this.repository.remove(entity);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static RemoveLibraryEntityUseCase getInstance() {
        return RemoveLibraryEntityUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveLibraryEntityUseCaseHolder {

        private static final RemoveLibraryEntityUseCase INSTANCE =
                new RemoveLibraryEntityUseCase(LibraryEntityRepository.getInstance());
    }
}
