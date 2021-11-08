/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntitiesByFilters;

import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.modules.library.infra.services.LibraryQueryService;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityDetails;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.GetLibraryEntitiesByFiltersRequestDTO;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.infra.services.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersUseCase implements IUsecase<GetLibraryEntitiesByFiltersRequestDTO, Set<LibraryEntityDetails>> {
    
    private final ILibraryQueryService queryService;
    
    private GetLibraryEntitiesByFiltersUseCase(
            ILibraryQueryService queryService
    ) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<Set<LibraryEntityDetails>> execute(GetLibraryEntitiesByFiltersRequestDTO request) throws GenericAppException {
        
        System.out.println("GetLibraryEntitiesByFiltersUsecase");
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
                        
            final Result<LibraryEntityType> typeOrError = LibraryEntityType.create(request.type);
            final String searchTerm = request.searchTerm != null ? request.searchTerm : null;
            final Boolean published = request.published != null ? request.published : null;
            final Boolean flagged = request.flagged != null ? request.flagged : null;
            final Boolean hasImage = request.hasImage != null ? request.hasImage : null;
            final Result<Set<UniqueEntityId>> genreIdsOrError = UniqueEntityId.createFromStrings(request.genreIds);
            final Result<Rate> rateFromOrError = Rate.create(request.rateFrom);
            final Result<Rate> rateToOrError = Rate.create(request.rateTo);
            
            final Result<UniqueEntityId> artistIdOrError = UniqueEntityId.createFromString(request.artistId);
            final Result<ReleaseDate> releaseDateFromOrError = ReleaseDate.create(request.releaseDateFrom);
            final Result<ReleaseDate> releaseDateTillOrError = ReleaseDate.create(request.releaseDateTill);
            
            final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromString(request.creatorId);
            final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromString(request.updaterId);
            final Result<DateTime> createdAtFromOrError = DateTime.create(request.createdAtFrom);
            final Result<DateTime> createdAtTillOrError = DateTime.create(request.createdAtTill);
            final Result<DateTime> lastModifiedAtFromOrError = DateTime.create(request.lastModifiedAtFrom);
            final Result<DateTime> lastModifiedAtTillOrError = DateTime.create(request.lastModifiedAtTill);
            
            if(request.type != null) combinedProps.add(typeOrError);
            if(request.rateFrom != null) combinedProps.add(rateFromOrError);
            if(request.rateTo != null) combinedProps.add(rateToOrError);
            if(request.rateFrom != null) combinedProps.add(rateFromOrError);
            if(request.genreIds != null) combinedProps.add(genreIdsOrError);
            
            if(request.artistId != null) combinedProps.add(artistIdOrError);
            if(request.releaseDateFrom != null) combinedProps.add(releaseDateFromOrError);
            if(request.releaseDateTill != null) combinedProps.add(releaseDateTillOrError);
            
            if(request.creatorId != null) combinedProps.add(creatorIdOrError);
            if(request.updaterId != null) combinedProps.add(updaterIdOrError);
            if(request.createdAtFrom != null) combinedProps.add(createdAtFromOrError);
            if(request.createdAtTill != null) combinedProps.add(createdAtTillOrError);
            if(request.lastModifiedAtFrom != null) combinedProps.add(lastModifiedAtFromOrError);
            if(request.lastModifiedAtTill != null) combinedProps.add(lastModifiedAtTillOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final LibraryEntityType type =
                    request.type != null ?
                    typeOrError.getValue() : null;
            final Set<UniqueEntityId> genreIds =
                    request.genreIds != null && !request.genreIds.isEmpty() ?
                    genreIdsOrError.getValue() :
                    null;
            final Rate rateFrom =
                    request.rateFrom != null ?
                    rateFromOrError.getValue() : null;
            final Rate rateTo =
                    request.rateTo != null ?
                    rateToOrError.getValue() : null;
            final Long totalPlayedCountFrom =
                    request.totalPlayedCountFrom;
            final Long totalPlayedCountTo =
                    request.totalPlayedCountTo;
            final Long durationFrom = request.durationFrom;
            final Long durationTo = request.durationTo;

            final UniqueEntityId artistId =
                    request.artistId != null ?
                    artistIdOrError.getValue() : null;
            final ReleaseDate releaseDateFrom =
                    request.releaseDateFrom != null ?
                    releaseDateFromOrError.getValue() : null;
            final ReleaseDate releaseDateTill =
                    request.releaseDateTill != null ?
                    releaseDateTillOrError.getValue() : null;
            
            final UniqueEntityId creatorId =
                    request.creatorId != null ?
                    creatorIdOrError.getValue() : null;
            final UniqueEntityId updaterId =
                    request.updaterId != null ?
                    updaterIdOrError.getValue() : null;
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
            
            final LibraryEntityFilters filters = new LibraryEntityFilters(
                    type,
                    searchTerm,
                    published,
                    flagged,
                    hasImage,
                    genreIds,
                    rateFrom,
                    rateTo,
                    totalPlayedCountFrom,
                    totalPlayedCountTo,
                    durationFrom,
                    durationTo,
                    artistId,
                    releaseDateFrom,
                    releaseDateTill,
                    creatorId,
                    updaterId,
                    createdAtFrom,
                    createdAtTill,
                    lastModifiedAtFrom,
                    lastModifiedAtTill
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
