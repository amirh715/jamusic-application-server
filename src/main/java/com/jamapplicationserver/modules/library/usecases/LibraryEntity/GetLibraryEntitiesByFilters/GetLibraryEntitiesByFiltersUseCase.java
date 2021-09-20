/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntitiesByFilters;

import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.GetLibraryEntitiesByFiltersRequestDTO;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.*;
import com.jamapplicationserver.modules.library.infra.services.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersUseCase implements IUsecase<GetLibraryEntitiesByFiltersRequestDTO, String> {
    
    private final ILibraryQueryService queryService;
    
    private GetLibraryEntitiesByFiltersUseCase(
            ILibraryQueryService queryService
    ) {
        this.queryService = queryService;
    }
    
    @Override
    public Result execute(GetLibraryEntitiesByFiltersRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
                        
            final String searchTerm = request.searchTerm != null ? request.searchTerm : null;
            final Result<DateTime> createdAtFromOrError = DateTime.create(request.createdAtFrom);
            final Result<DateTime> createdAtTillOrError = DateTime.create(request.createdAtTill);
            final Result<DateTime> lastModifiedAtFromOrError = DateTime.create(request.lastModifiedAtFrom);
            final Result<DateTime> lastModifiedAtTillOrError = DateTime.create(request.lastModifiedAtTill);
            final Result<LibraryEntityType> typeOrError = LibraryEntityType.create(request.type);
            final Result<Set<UniqueEntityId>> genreIdsOrError = UniqueEntityId.createFromStrings(request.genreIds);
            final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromString(request.creatorId);
            final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromString(request.updaterId);
            
            if(request.createdAtFrom != null) combinedProps.add(createdAtFromOrError);
            if(request.createdAtTill != null) combinedProps.add(createdAtTillOrError);
            if(request.lastModifiedAtFrom != null) combinedProps.add(lastModifiedAtFromOrError);
            if(request.lastModifiedAtTill != null) combinedProps.add(lastModifiedAtTillOrError);
            if(request.type != null) combinedProps.add(typeOrError);
            if(request.genreIds != null) combinedProps.add(genreIdsOrError);
            if(request.creatorId != null) combinedProps.add(creatorIdOrError);
            if(request.updaterId != null) combinedProps.add(updaterIdOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final DateTime createdAtFrom =
                    request.createdAtFrom != null ?
                    createdAtFromOrError.getValue() : null;
            final DateTime createdAtTill =
                    request.createdAtTill != null ?
                    createdAtTillOrError.getValue() : null;
            final DateTime lastModifiedAtFrom =
                    request.lastModifiedAtFrom != null ?
                    lastModifiedAtFromOrError.getValue() : null;
            final DateTime lastModifiedAtTill =
                    request.lastModifiedAtTill != null ?
                    lastModifiedAtTillOrError.getValue() : null;
            final LibraryEntityType type =
                    request.type != null ?
                    typeOrError.getValue() : null;
            
            final Set<UniqueEntityId> genreIds =
                    request.genreIds != null && !request.genreIds.isEmpty() ?
                    genreIdsOrError.getValue() :
                    null;
            final UniqueEntityId creatorId =
                    request.creatorId != null ?
                    creatorIdOrError.getValue() : null;
            final UniqueEntityId updaterId =
                    request.updaterId != null ?
                    updaterIdOrError.getValue() : null;
            
            final LibraryEntityFilters filters = new LibraryEntityFilters(
                    createdAtFrom,
                    createdAtTill,
                    lastModifiedAtFrom,
                    lastModifiedAtTill,
                    searchTerm,
                    type,
                    request.rateFrom,
                    request.rateTo,
                    request.published,
                    request.flagged,
                    request.hasImage,
                    genreIds,
                    creatorId,
                    updaterId
            );
            
            final Set<LibraryEntityDetails> entities =
                    queryService.getLibraryEntitiesByFilters(filters);
            
            return Result.ok(entities);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetLibraryEntitiesByFiltersUseCase getInstance() {
        return GetLibraryEntitiesUseCaseHolder.INSTANCE;
    }
    
    private static class GetLibraryEntitiesUseCaseHolder {

        private static final GetLibraryEntitiesByFiltersUseCase INSTANCE =
                new GetLibraryEntitiesByFiltersUseCase(
                        LibraryQueryService.getInstance()
                );
    }
}
