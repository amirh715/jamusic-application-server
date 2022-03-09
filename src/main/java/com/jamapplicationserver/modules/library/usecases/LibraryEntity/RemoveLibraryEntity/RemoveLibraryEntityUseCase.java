/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.RemoveLibraryEntity;

import com.jamapplicationserver.modules.library.infra.DTOs.commands.RemoveLibraryEntityRequestDTO;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class RemoveLibraryEntityUseCase implements IUsecase<RemoveLibraryEntityRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    
    private RemoveLibraryEntityUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(RemoveLibraryEntityRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            
            final Result[] combinedProps = {
                idOrError
            };
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final LibraryEntity entity =
                    repository.fetchById(id)
                            .includeUnpublished(request.subjectRole)
                            .getSingleResult();
            if(entity == null) return Result.fail(new LibraryEntityDoesNotExistError());
            
            // #### DOMAIN LOGIC LEAK ####
            if(!request.subjectRole.isAdmin())
                return Result.fail(new LibraryEntityCannotBeRemovedByAnyoneOtherThanTheAdmin());

            repository.remove(entity);
            
            return Result.ok();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
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
