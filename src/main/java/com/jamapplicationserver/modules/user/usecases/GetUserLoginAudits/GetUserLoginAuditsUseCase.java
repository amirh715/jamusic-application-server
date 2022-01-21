/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserLoginAudits;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.services.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.user.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.user.domain.errors.*;

/**
 *
 * @author dada
 */
public class GetUserLoginAuditsUseCase implements IUsecase<GetLoginAuditsRequestDTO, Set<LoginDetails>> {
    
    private final IUserQueryService queryService;
    
    private GetUserLoginAuditsUseCase(IUserQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<Set<LoginDetails>> execute(GetLoginAuditsRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Set<LoginDetails> logins = queryService.getLoginsOfUser(id, request.limit, request.offset);
            if(logins == null) return Result.fail(new UserDoesNotExistError());
            
            return Result.ok(logins);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetUserLoginAuditsUseCase getInstance() {
        return GetUserLoginAuditsUseCaseHolder.INSTANCE;
    }
    
    private static class GetUserLoginAuditsUseCaseHolder {

        private static final GetUserLoginAuditsUseCase INSTANCE =
                new GetUserLoginAuditsUseCase(UserQueryService.getInstance());
    }
}
