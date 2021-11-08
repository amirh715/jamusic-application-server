/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.GetGenreById;

import com.jamapplicationserver.modules.library.infra.services.LibraryQueryService;
import com.jamapplicationserver.modules.library.domain.core.Genre;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.services.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.GenreDetails;

/**
 *
 * @author dada
 */
public class GetGenreByIdUseCase implements IUsecase<String, GenreDetails> {
    
    private final ILibraryQueryService queryService;
    
    private GetGenreByIdUseCase(ILibraryQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<GenreDetails> execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
            
            final GenreDetails genre = queryService.getGenreById(id);
            
            if(genre == null) return Result.fail(new GenreDoesNotExistError());
            
            return Result.ok(genre);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetGenreByIdUseCase getInstance() {
        return GetGenreByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetGenreByIdUseCaseHolder {

        private static final GetGenreByIdUseCase INSTANCE =
                new GetGenreByIdUseCase(LibraryQueryService.getInstance());
    }
}
