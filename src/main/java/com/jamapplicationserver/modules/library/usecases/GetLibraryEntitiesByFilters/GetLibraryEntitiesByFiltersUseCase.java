/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetLibraryEntitiesByFilters;

import java.util.*;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.GetLibraryEntitiesByFiltersRequestDTO;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.repositories.mappers.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersUseCase implements IUseCase<GetLibraryEntitiesByFiltersRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    
    private GetLibraryEntitiesByFiltersUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(GetLibraryEntitiesByFiltersRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final String searchTerm = request.searchTerm;
            final Result<DateTime> createdAtFromOrError = DateTime.create(request.createdAtFrom);
            final Result<DateTime> createdAtTillOrError = DateTime.create(request.createdAtTill);
            final Result<DateTime> lastModifiedAtFromOrError = DateTime.create(request.lastModifiedAtFrom);
            final Result<DateTime> lastModifiedAtTillOrError = DateTime.create(request.lastModifiedAtTill);
            final Result<LibraryEntityType> typeOrError = LibraryEntityType.create(request.type);
//            final Result<GenreList> genresOrError = GenreList.create(GenreMapper.toDomain(request.genres));
            final Result<UniqueEntityID> creatorIdOrError = UniqueEntityID.createFromString(request.creatorId);
            final Result<UniqueEntityID> updaterIdOrError = UniqueEntityID.createFromString(request.updaterId);
            
            if(request.createdAtFrom != null) combinedProps.add(createdAtFromOrError);
            if(request.createdAtTill != null) combinedProps.add(createdAtTillOrError);
            if(request.lastModifiedAtFrom != null) combinedProps.add(lastModifiedAtFromOrError);
            if(request.lastModifiedAtTill != null) combinedProps.add(lastModifiedAtTillOrError);
            if(request.type != null) combinedProps.add(typeOrError);
//            if(request.genres != null) combinedProps.add(genresOrError);
            if(request.creatorId != null) combinedProps.add(creatorIdOrError);
            if(request.updaterId != null) combinedProps.add(updaterIdOrError);
            
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final DateTime createdAtFrom = createdAtFromOrError.getValue();
            final DateTime createdAtTill = createdAtTillOrError.getValue();
            final DateTime lastModifiedAtFrom = lastModifiedAtFromOrError.getValue();
            final DateTime lastModifiedAtTill = lastModifiedAtTillOrError.getValue();
            final LibraryEntityType type = typeOrError.getValue();
//            final GenreList genres = genresOrError.getValue();
            final UniqueEntityID creatorId = creatorIdOrError.getValue();
            final UniqueEntityID updaterId = updaterIdOrError.getValue();
            
            final LibraryEntityFilters filters = new LibraryEntityFilters(
                    createdAtFrom,
                    createdAtTill,
                    lastModifiedAtFrom,
                    lastModifiedAtTill,
                    searchTerm,
                    type,
                    request.published,
                    request.flagged,
                    request.hasImage,
//                    genres,
                    null,
                    creatorId,
                    updaterId
            );
            
            final Set<LibraryEntity> entities =
                    this.repository.fetchByFilters(filters).getResults();
            
            return Result.ok(entities);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetLibraryEntitiesByFiltersUseCase getInstance() {
        return GetLibraryEntitiesUseCaseHolder.INSTANCE;
    }
    
    private static class GetLibraryEntitiesUseCaseHolder {

        private static final GetLibraryEntitiesByFiltersUseCase INSTANCE =
                new GetLibraryEntitiesByFiltersUseCase(LibraryEntityRepository.getInstance());
    }
}
