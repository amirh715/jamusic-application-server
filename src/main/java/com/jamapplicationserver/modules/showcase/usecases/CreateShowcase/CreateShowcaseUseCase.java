/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.CreateShowcase;

import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.showcase.infra.DTOs.CreateShowcaseRequestDTO;
import com.jamapplicationserver.modules.showcase.repository.*;
import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.infra.Persistence.filesystem.*;

/**
 *
 * @author dada
 */
public class CreateShowcaseUseCase implements IUsecase<CreateShowcaseRequestDTO, String> {
    
    private final IShowcaseRepository repository;
    private final IFilePersistenceManager persistence;
    
    private CreateShowcaseUseCase(
            IShowcaseRepository repository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(CreateShowcaseRequestDTO request) throws GenericAppException {
        
        try {

            final Result<Showcase> showcaseOrError =
                    Showcase.create(
                            request.index != null ? Integer.decode(request.index) : null,
                            request.title,
                            request.description,
                            request.route,
                            request.subjectId
                    );
            if(showcaseOrError.isFailure) return showcaseOrError;
            
            final Showcase showcase = showcaseOrError.getValue();
            
            repository.save(showcase);
            
            return Result.ok();
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static CreateShowcaseUseCase getInstance() {
        return CreateShowcaseUseCaseHolder.INSTANCE;
    }
    
    private static class CreateShowcaseUseCaseHolder {

        private static final CreateShowcaseUseCase INSTANCE =
                new CreateShowcaseUseCase(
                        ShowcaseRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
