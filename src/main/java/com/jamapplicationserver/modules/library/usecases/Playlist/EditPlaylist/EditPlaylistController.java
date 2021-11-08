/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.EditPlaylist;

import java.util.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.EditPlaylistRequestDTO;

/**
 *
 * @author dada
 */
public class EditPlaylistController extends BaseController {
    
    private final IUsecase<EditPlaylistRequestDTO, String> usecase;
    
    private EditPlaylistController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields =
                    MultipartFormDataUtil.toMap(req.raw());
            
            final EditPlaylistRequestDTO dto =
                    new EditPlaylistRequestDTO(
                            fields.get("id"),
                            fields.get("title"),
                            fields.get("trackIds"),
                            subjectId,
                            subjectRole
                    );
            
            try {
                
                final Result result = usecase.execute(dto);
                
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
                
                noContent();
                
            } catch(Exception e) {
                fail(e);
            }
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static EditPlaylistController getInstance() {
        return EditPlaylistUseCaseHolder.INSTANCE;
    }
    
    private static class EditPlaylistUseCaseHolder {

        private static final EditPlaylistController INSTANCE =
                new EditPlaylistController(EditPlaylistUseCase.getInstance());
    }
}
