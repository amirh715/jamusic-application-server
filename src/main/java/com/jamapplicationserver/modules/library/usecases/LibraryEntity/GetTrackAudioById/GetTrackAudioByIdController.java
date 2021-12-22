/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetTrackAudioById;

import java.io.InputStream;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.*;

/**
 *
 * @author dada
 */
public class GetTrackAudioByIdController extends BaseController {
    
    private final IUsecase<GetTrackAudioByIdRequestDTO, InputStream> usecase;
    
    private GetTrackAudioByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final GetTrackAudioByIdRequestDTO dto =
                    new GetTrackAudioByIdRequestDTO(
                            req.params(":id"),
                            subjectId,
                            subjectRole
                    );
            
            final Result<InputStream> result = usecase.execute(dto);
            
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
            
            sendFile(result.getValue());
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
        }
        
    }
    
    public static GetTrackAudioByIdController getInstance() {
        return GetTrackAudioByIdHolder.INSTANCE;
    }
    
    private static class GetTrackAudioByIdHolder {

        private static final GetTrackAudioByIdController INSTANCE =
                new GetTrackAudioByIdController(GetTrackAudioByIdUseCase.getInstance());
    }
}
