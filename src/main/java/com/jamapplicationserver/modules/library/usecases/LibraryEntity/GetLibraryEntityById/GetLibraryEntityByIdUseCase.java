/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityById;

import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.services.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityDetails;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.GetLibraryEntityByIdRequestDTO;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntityByIdUseCase implements IUsecase<GetLibraryEntityByIdRequestDTO, LibraryEntityDetails> {
    
    private final ILibraryQueryService queryService;
    
    private GetLibraryEntityByIdUseCase(ILibraryQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<LibraryEntityDetails> execute(GetLibraryEntityByIdRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
            final LibraryEntityDetails entity = queryService.getLibraryEntityById(id);
            if(entity == null)
                return Result.fail(new LibraryEntityDoesNotExistError());
            
            return Result.ok(entity);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetLibraryEntityByIdUseCase getInstance() {
        return GetLibraryEntityByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityByIdUseCaseHolder {

        private static final GetLibraryEntityByIdUseCase INSTANCE =
                new GetLibraryEntityByIdUseCase(LibraryQueryService.getInstance());
    }
}
