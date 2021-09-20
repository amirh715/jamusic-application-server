/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.CreateShowcase;

import java.util.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.showcase.infra.DTOs.CreateShowcaseRequestDTO;

/**
 *
 * @author dada
 */
public class CreateShowcaseController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreateShowcaseController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields =
                    MultipartFormDataUtil.toMap(this.req.raw());
            
            final CreateShowcaseRequestDTO dto =
                    new CreateShowcaseRequestDTO(
                            fields.get("index"),
                            fields.get("title"),
                            fields.get("description"),
                            fields.get("route"),
                            null,
                            fields.get("creatorId")
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
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static CreateShowcaseController getInstance() {
        return CreateShowcaseControllerHolder.INSTANCE;
    }
    
    private static class CreateShowcaseControllerHolder {

        private static final CreateShowcaseController INSTANCE =
                new CreateShowcaseController(CreateShowcaseUseCase.getInstance());
    }
}
