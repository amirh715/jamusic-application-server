/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.PublishOrArchiveLibraryEntity;

import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class PublishArchiveLibraryEntityUseCase implements IUsecase<PublishOrArchiveLibraryEntityRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    
    private PublishArchiveLibraryEntityUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(PublishOrArchiveLibraryEntityRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final LibraryEntity entity =
                    repository.fetchById(id)
                            .includeUnpublished(request.subjectRole)
                            .getSingleResult();
            
            if(entity == null) return Result.fail(new LibraryEntityDoesNotExistError());
            
            if(entity instanceof Artist) {
                if(request.publish)
                    ((Artist) entity).publish(request.subjectId, request.cascadePublishCommandToArtistsArtworks);
                else
                    entity.archive(request.subjectId);
            } else {
                if(request.publish)
                    entity.publish(request.subjectId);
                else
                    entity.archive(request.subjectId);
            }

            repository.save(entity);
            
            return Result.ok();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
        
    }
    
    public static PublishArchiveLibraryEntityUseCase getInstance() {
        return PublishArchiveLibraryEntityUseCaseHolder.INSTANCE;
    }
    
    private static class PublishArchiveLibraryEntityUseCaseHolder {

        private static final PublishArchiveLibraryEntityUseCase INSTANCE =
                new PublishArchiveLibraryEntityUseCase(LibraryEntityRepository.getInstance());
    }
}
