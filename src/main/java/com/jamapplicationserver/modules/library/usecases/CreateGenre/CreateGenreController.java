/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.CreateGenre;

import java.util.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateGenreRequestDTO;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;

/**
 *
 * @author dada
 */
public class CreateGenreController extends BaseController {
    
    private final IUseCase usecase;
    
    private CreateGenreController(IUseCase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        
            final CreateGenreRequestDTO dto =
                    new CreateGenreRequestDTO(
                            fields.get("title"),
                            fields.get("titleInPersian"),
                            fields.get("parentGenreId")
                    );
            
            final Result result = this.usecase.execute(dto);
            
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
            
            this.created(result.getValue());
            
        } catch(Exception e) {
            e.printStackTrace();
            this.fail(e);
        }
        
    }
    
    public static CreateGenreController getInstance() {
        return CreateGenreControllerHolder.INSTANCE;
    }
    
    private static class CreateGenreControllerHolder {

        private static final CreateGenreController INSTANCE =
                new CreateGenreController(CreateGenreUseCase.getInstance());
    }
}
