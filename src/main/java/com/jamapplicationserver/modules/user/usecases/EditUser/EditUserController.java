/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.EditUser;

import java.util.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import java.io.*;

/**
 *
 * @author amirhossein
 */
public class EditUserController extends BaseController {
    
    private final IUsecase useCase;
    
    private EditUserController(IUsecase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("EditUserController");
        
        try {
            
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
            final InputStream profileImage = MultipartFormDataUtil
                    .toInputStream(
                            req.raw().getPart("profileImage") != null ?
                            req.raw().getPart("profileImage") : null
                    );
            final EditUserRequestDTO dto = new EditUserRequestDTO(
                    fields.get("id"),
                    fields.get("name"),
                    fields.get("email"),
                    Boolean.valueOf(fields.get("removeEmail")),
                    fields.get("role"),
                    profileImage,
                    Boolean.valueOf(fields.get("removeProfileImage")),
                    subjectId,
                    subjectRole
            );
            
            final Result result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                if(error instanceof ConflictError)
                    conflict(error);
                
                if(error instanceof NotFoundError)
                    notFound(error);
             
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
        }
        
    }
    
    public static EditUserController getInstance() {
        return EditUserControllerHolder.INSTANCE;
    }
    
    private static class EditUserControllerHolder {

        private static final EditUserController INSTANCE =
                new EditUserController(EditUserUseCase.getInstance());
    }
}
