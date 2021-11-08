/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateAlbum;

import java.util.*;
import java.util.stream.*;
import javax.servlet.http.Part;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.CreateAlbumRequestDTO;

/**
 *
 * @author dada
 */
public class CreateAlbumController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreateAlbumController(IUsecase usecase) {
        this.usecase = usecase;
        this.requireAuthClaims = true;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            System.out.println("CreateAlbumController");
            
            final HttpServletRequest raw = this.req.raw();
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(raw);
                        
            final InputStream image = MultipartFormDataUtil.toInputStream(raw.getPart("image"));
            
            final CreateAlbumRequestDTO dto =
                    new CreateAlbumRequestDTO(
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("genreIds"),
                            fields.get("tags"),
                            fields.get("flagNote"),
                            fields.get("artistId"),
                            subjectId,
                            subjectRole,
                            fields.get("recordLabel"),
                            fields.get("producer"),
                            fields.get("releaseYear"),
                            image
                    );
            
            final Result result = this.usecase.execute(dto);
            
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
            
            created();
            
        } catch(Exception e) {
            e.printStackTrace();
            this.fail(e);
        }
        
    }
    
    public static CreateAlbumController getInstance() {
        return CreateAlbumControllerHolder.INSTANCE;
    }
    
    private static class CreateAlbumControllerHolder {

        private static final CreateAlbumController INSTANCE =
                new CreateAlbumController(CreateAlbumUseCase.getInstance());
    }
}
