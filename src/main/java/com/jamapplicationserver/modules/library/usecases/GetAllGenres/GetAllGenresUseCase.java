/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetAllGenres;

import java.util.Set;
import com.jamapplicationserver.modules.library.domain.core.Genre;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetAllGenresUseCase implements IUseCase {
    
    private final IGenreRepository repository;
    
    private GetAllGenresUseCase(IGenreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(Object obj) throws GenericAppException {
        
        try {
            
            final Set<Genre> genres = this.repository.fetchAll();
            
            return Result.ok(genres);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetAllGenresUseCase getInstance() {
        return GetAllGenresUseCaseHolder.INSTANCE;
    }
    
    private static class GetAllGenresUseCaseHolder {

        private static final GetAllGenresUseCase INSTANCE =
                new GetAllGenresUseCase(GenreRepository.getInstance());
    }
}
