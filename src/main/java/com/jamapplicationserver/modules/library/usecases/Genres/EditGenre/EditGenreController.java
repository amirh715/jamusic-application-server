/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.EditGenre;

import java.util.Map;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.EditGenreRequestDTO;

/**
 *
 * @author dada
 */
public class EditGenreController extends BaseController {
    
    private final IUsecase usecase;
    
    private EditGenreController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final EditGenreRequestDTO dto = new EditGenreRequestDTO(
                    fields.get("id"),
                    fields.get("title"),
                    fields.get("titleInPersian"),
                    subjectId,
                    subjectRole
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
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static EditGenreController getInstance() {
        return EditGenreControllerHolder.INSTANCE;
    }
    
    private static class EditGenreControllerHolder {

        private static final EditGenreController INSTANCE =
                new EditGenreController(EditGenreUseCase.getInstance());
    }
}
