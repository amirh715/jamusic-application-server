/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityById;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.GetLibraryEntityByIdRequestDTO;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityDetails;

/**
 *
 * @author dada
 */
public class GetLibraryEntityByIdController extends BaseController {
    
    private final IUsecase<GetLibraryEntityByIdRequestDTO, LibraryEntityDetails> usecase;
    
    private GetLibraryEntityByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }

    @Override
    public void executeImpl() {
        
        try {
            
            final String id = req.params(":id");
            
            final GetLibraryEntityByIdRequestDTO dto =
                    new GetLibraryEntityByIdRequestDTO(
                            id,
                            subjectId,
                            subjectRole
                    );
            
            final Result<LibraryEntityDetails> result = usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                if(error instanceof ConflictError)
                    conflict(error);
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                return;
            }
            
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetLibraryEntityByIdController getInstance() {
        return GetLibraryEntityByIdControllerHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityByIdControllerHolder {

        private static final GetLibraryEntityByIdController INSTANCE =
                new GetLibraryEntityByIdController(GetLibraryEntityByIdUseCase.getInstance());
    }
}
