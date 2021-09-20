/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.GetGenreByTitle;

import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class GetGenreByTitleUseCase implements IUsecase<String, String> {
    
    private final IGenreRepository repository;
    
    private GetGenreByTitleUseCase(IGenreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(String title) throws GenericAppException {
        
        try {
            
            final Result<GenreTitle> titleOrError = GenreTitle.create(title);
            
            if(titleOrError.isFailure) return titleOrError;
            
            final Genre genre = this.repository.fetchByTitle(titleOrError.getValue());
            
            if(genre == null) return Result.fail(new GenreDoesNotExistError());
            
            return Result.ok(genre);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetGenreByTitleUseCase getInstance() {
        return GetGenreByTitleUseCaseHolder.INSTANCE;
    }
    
    private static class GetGenreByTitleUseCaseHolder {

        private static final GetGenreByTitleUseCase INSTANCE =
                new GetGenreByTitleUseCase(GenreRepository.getInstance());
    }
}
