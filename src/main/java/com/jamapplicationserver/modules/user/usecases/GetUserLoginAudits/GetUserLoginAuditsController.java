/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserLoginAudits;

import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.user.infra.DTOs.commands.GetLoginAuditsRequestDTO;

/**
 *
 * @author dada
 */
public class GetUserLoginAuditsController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetUserLoginAuditsController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final GetLoginAuditsRequestDTO dto =
                    new GetLoginAuditsRequestDTO(
                            req.params(":id"),
                            req.queryMap("limit").integerValue(),
                            req.queryMap("offset").integerValue(),
                            subjectId,
                            subjectRole
                    );
            
            final Result<Set<LoginDetails>> result = usecase.execute(dto);
            
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
            
            noStore();
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetUserLoginAuditsController getInstance() {
        return GetUserLoginAuditsControllerHolder.INSTANCE;
    }
    
    private static class GetUserLoginAuditsControllerHolder {

        private static final GetUserLoginAuditsController INSTANCE =
                new GetUserLoginAuditsController(GetUserLoginAuditsUseCase.getInstance());
    }
}
