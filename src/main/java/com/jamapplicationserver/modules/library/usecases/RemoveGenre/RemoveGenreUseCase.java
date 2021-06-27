/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.RemoveGenre;

import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 */
public class RemoveGenreUseCase implements IUseCase<String, Genre> {
    
    private final IGenreRepository repository;
    
    private RemoveGenreUseCase(IGenreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(String id) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromString(id);
            
            if(idOrError.isFailure) return idOrError;
            
            final Genre genre = this.repository.fetchById(idOrError.getValue());
            
            if(genre == null) return Result.fail(new GenreDoesNotExistError());
            
            this.repository.remove(genre);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static RemoveGenreUseCase getInstance() {
        return RemoveGenreUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveGenreUseCaseHolder {

        private static final RemoveGenreUseCase INSTANCE =
                new RemoveGenreUseCase(GenreRepository.getInstance());
    }
}
