/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.GetGenreById;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetGenreByIdController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetGenreByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
                        
            final String id = this.req.params("id");
                        
            final Result result = this.usecase.execute(id);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                if(error instanceof ConflictError)
                    this.conflict(error);
                
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static GetGenreByIdController getInstance() {
        return GetGenreByIdControllerHolder.INSTANCE;
    }
    
    private static class GetGenreByIdControllerHolder {

        private static final GetGenreByIdController INSTANCE =
                new GetGenreByIdController(GetGenreByIdUseCase.getInstance());
    }
}
