/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.CreatePlaylist;

import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IUsecase;

/**
 *
 * @author dada
 */
public class CreatePlaylistController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreatePlaylistController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Result result = this.usecase.execute(null);
            
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
            
            this.noContent();
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static CreatePlaylistController getInstance() {
        return CreatePlaylistControllerHolder.INSTANCE;
    }
    
    private static class CreatePlaylistControllerHolder {

        private static final CreatePlaylistController INSTANCE =
                new CreatePlaylistController(CreatePlaylistUseCase.getInstance());
    }
}
