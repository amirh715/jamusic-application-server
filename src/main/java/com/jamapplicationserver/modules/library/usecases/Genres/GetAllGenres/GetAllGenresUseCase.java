/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.GetAllGenres;

import com.jamapplicationserver.modules.library.infra.services.LibraryQueryService;
import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.core.Genre;
import com.jamapplicationserver.modules.library.infra.services.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.GenreDetails;

/**
 *
 * @author dada
 */
public class GetAllGenresUseCase implements IUsecase {
    
    private final ILibraryQueryService queryService;
    
    private GetAllGenresUseCase(ILibraryQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<GenreDetails> execute(Object obj) throws GenericAppException {
        
        try {
            
            return Result.ok(queryService.getAllGenres());
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetAllGenresUseCase getInstance() {
        return GetAllGenresUseCaseHolder.INSTANCE;
    }
    
    private static class GetAllGenresUseCaseHolder {

        private static final GetAllGenresUseCase INSTANCE =
                new GetAllGenresUseCase(LibraryQueryService.getInstance());
    }
}
