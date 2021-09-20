/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityImageById;

import com.jamapplicationserver.infra.Persistence.filesystem.FilePersistenceManager;
import java.io.File;
import java.io.InputStream;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.domain.core.LibraryEntity;
import com.jamapplicationserver.infra.Persistence.filesystem.IFilePersistenceManager;

/**
 *
 * @author dada
 */
public class GetLibraryEntityImageByIdUseCase implements IUsecase<String, String> {
    
    private final ILibraryEntityRepository repository;
    private final IFilePersistenceManager persistence;
    
    private GetLibraryEntityImageByIdUseCase(
            ILibraryEntityRepository repository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final LibraryEntity entity =
                    this.repository.fetchById(id).getSingleResult();
            
            if(entity == null) return Result.fail(new LibraryEntityDoesNotExistError());
            
            // get image
            
            final File file = entity.getImagePath().toFile();
            
            final InputStream stream = this.persistence.read(file);
            
            return Result.ok(stream);
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetLibraryEntityImageByIdUseCase getInstance() {
        return GetLibraryEntityImageByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityImageByIdUseCaseHolder {

        private static final GetLibraryEntityImageByIdUseCase INSTANCE =
                new GetLibraryEntityImageByIdUseCase(
                        LibraryEntityRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
