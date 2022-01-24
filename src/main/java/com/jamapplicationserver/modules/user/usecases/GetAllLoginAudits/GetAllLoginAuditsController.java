/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetAllLoginAudits;

import spark.*;
import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.user.infra.DTOs.commands.*;

/**
 *
 * @author dada
 */
public class GetAllLoginAuditsController extends BaseController {
    
    private final IUsecase<GetAllLoginAuditsRequestDTO, HashSet<LoginDetails>> usecase;
    
    private GetAllLoginAuditsController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final QueryParamsMap fields = req.queryMap();
            
            final GetAllLoginAuditsRequestDTO dto =
                    new GetAllLoginAuditsRequestDTO(
                            fields.get("limit").integerValue(),
                            fields.get("offset").integerValue(),
                            subjectId,
                            subjectRole
                    );
            
            final Result<HashSet<LoginDetails>> result = usecase.execute(dto);
            
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
    
    public static GetAllLoginAuditsController getInstance() {
        return GetAllLoginAuditsControllerHolder.INSTANCE;
    }
    
    private static class GetAllLoginAuditsControllerHolder {

        private static final GetAllLoginAuditsController INSTANCE =
                new GetAllLoginAuditsController(GetAllLoginAuditsUseCase.getInstance());
    }
}
