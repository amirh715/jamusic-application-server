/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.PublishOrArchiveLibraryEntity;

import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.PublishArchiveLibraryEntityRequestDTO;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class PublishArchiveLibraryEntityUseCase implements IUseCase<PublishArchiveLibraryEntityRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    
    private PublishArchiveLibraryEntityUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(PublishArchiveLibraryEntityRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromString(request.id);
            final Boolean state = Boolean.getBoolean(request.state);
            
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityID id = idOrError.getValue();
            
            final LibraryEntity entity =
                    this.repository.fetchById(id)
                            .includeUnpublished()
                            .getSingleResult();
            
            if(entity == null) return Result.fail(new LibraryEntityDoesNotExistError());
            
            if(state)
                entity.publish();
            else
                entity.archive();
            
            this.repository.save(entity);
            
            return Result.ok();
            
        } catch(Exception e) {
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
