/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetAllLoginAudits;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.user.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.user.infra.services.*;

/**
 *
 * @author dada
 */
public class GetAllLoginAuditsUseCase implements IUsecase<GetAllLoginAuditsRequestDTO, Set<LoginDetails>> {
    
    private IUserQueryService queryService;
    
    private GetAllLoginAuditsUseCase(IUserQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<Set<LoginDetails>> execute(GetAllLoginAuditsRequestDTO request) throws GenericAppException {
        
        try {
            
            return Result.ok(queryService.getAllLogins(request.limit, request.offset));
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetAllLoginAuditsUseCase getInstance() {
        return GetAllLoginAuditsUseCaseHolder.INSTANCE;
    }
    
    private static class GetAllLoginAuditsUseCaseHolder {

        private static final GetAllLoginAuditsUseCase INSTANCE =
                new GetAllLoginAuditsUseCase(UserQueryService.getInstance());
    }
}
