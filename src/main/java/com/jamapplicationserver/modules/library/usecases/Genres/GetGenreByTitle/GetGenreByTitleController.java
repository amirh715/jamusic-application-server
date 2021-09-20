/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.GetGenreByTitle;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetGenreByTitleController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetGenreByTitleController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final String title = this.req.params("title");
            
            final Result result = this.usecase.execute(title);
            
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
    
    public static GetGenreByTitleController getInstance() {
        return GetGenreByTitleControllerHolder.INSTANCE;
    }
    
    private static class GetGenreByTitleControllerHolder {

        private static final GetGenreByTitleController INSTANCE =
                new GetGenreByTitleController(GetGenreByTitleUseCase.getInstance());
    }
}
