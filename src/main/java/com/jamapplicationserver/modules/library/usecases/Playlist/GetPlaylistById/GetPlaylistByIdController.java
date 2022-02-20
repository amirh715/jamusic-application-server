/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlaylistById;

import java.time.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;

/**
 *
 * @author dada
 */
public class GetPlaylistByIdController extends BaseController {
    
    private IUsecase<GetPlaylistByIdRequestDTO, PlaylistDetails> usecase;
    
    private GetPlaylistByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final GetPlaylistByIdRequestDTO dto =
                    new GetPlaylistByIdRequestDTO(
                            req.params(":id"),
                            subjectId,
                            subjectRole
                    );
            
            final Result<PlaylistDetails> result = usecase.execute(dto);
            
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
            
            if(subjectRole.isSubscriber()) {
                privateCache();
                cache(Duration.ofMinutes(5));
            }
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetPlaylistByIdController getInstance() {
        return GetPlaylistByIdControllerHolder.INSTANCE;
    }
    
    private static class GetPlaylistByIdControllerHolder {

        private static final GetPlaylistByIdController INSTANCE =
                new GetPlaylistByIdController(GetPlaylistByIdUseCase.getInstance());
    }
}
