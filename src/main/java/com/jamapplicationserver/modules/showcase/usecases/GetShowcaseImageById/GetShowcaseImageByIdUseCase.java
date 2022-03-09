/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetShowcaseImageById;

import java.io.*;
import java.nio.file.Path;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.showcase.repository.*;
import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.modules.showcase.domain.errors.*;

/**
 *
 * @author dada
 */
public class GetShowcaseImageByIdUseCase implements IUsecase<String, InputStream> {
    
    private IFilePersistenceManager persistence;
    private IShowcaseRepository repository;
    
    private GetShowcaseImageByIdUseCase(
            IFilePersistenceManager persistence,
            IShowcaseRepository repository
    ) {
        this.persistence = persistence;
        this.repository = repository;
    }
    
    @Override
    public Result<InputStream> execute(String idString) throws GenericAppException {
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Showcase showcase = repository.fetchById(id);
            if(showcase == null) return Result.fail(new ShowcaseDoesNotExistError());
            
            final Path imagePath = Path.of(showcase.getImagePath());
            final InputStream image = persistence.read(imagePath.toFile());
            
            if(image == null) return Result.fail(new ShowcaseImageDoesNotExistError());
            
            return Result.ok(image);
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
    }
    
    public static GetShowcaseImageByIdUseCase getInstance() {
        return GetShowcaseImageByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetShowcaseImageByIdUseCaseHolder {

        private static final GetShowcaseImageByIdUseCase INSTANCE =
                new GetShowcaseImageByIdUseCase(
                        FilePersistenceManager.getInstance(),
                        ShowcaseRepository.getInstance()
                );
    }
}
