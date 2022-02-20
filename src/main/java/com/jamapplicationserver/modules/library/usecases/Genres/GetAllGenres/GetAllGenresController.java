/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.GetAllGenres;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.GenreDetails;

/**
 *
 * @author dada
 */
public class GetAllGenresController extends BaseController {
    
    private final IUsecase<String, Set<GenreDetails>> usecase;
    
    private GetAllGenresController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Result<Set<GenreDetails>> result = usecase.execute("");
            
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
            
            noStore();
            ok(result.getValue());
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
        }
        
    }
    
    public static GetAllGenresController getInstance() {
        return GetAllGenresControllerHolder.INSTANCE;
    }
    
    private static class GetAllGenresControllerHolder {

        private static final GetAllGenresController INSTANCE =
                new GetAllGenresController(GetAllGenresUseCase.getInstance());
    }
}
