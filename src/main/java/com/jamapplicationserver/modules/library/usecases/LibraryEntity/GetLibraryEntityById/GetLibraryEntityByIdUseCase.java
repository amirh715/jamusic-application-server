/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityById;

import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;

/**
 *
 * @author dada
 */
public class GetLibraryEntityByIdUseCase implements IUsecase<String, String> {
    
    private final IGenreRepository repository;
    
    private GetLibraryEntityByIdUseCase(IGenreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(String id) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(id);
            
            if(idOrError.isFailure) return idOrError;
            
            final Genre genre = this.repository.fetchById(idOrError.getValue());
            
            return Result.ok(genre);
            
        } catch(Exception e) {
            return Result.ok();
        }
        
    }
    
    public static GetLibraryEntityByIdUseCase getInstance() {
        return GetLibraryEntityByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityByIdUseCaseHolder {

        private static final GetLibraryEntityByIdUseCase INSTANCE =
                new GetLibraryEntityByIdUseCase(GenreRepository.getInstance());
    }
}
