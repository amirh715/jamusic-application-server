/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetMyProfileInfo;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.user.infra.DTOs.commands.*;
import com.jamapplicationserver.modules.user.infra.services.*;

/**
 *
 * @author dada
 */
public class GetMyProfileInfoUseCase implements IUsecase<GetMyProfileInfoRequestDTO, MyProfileDetails> {
    
    private IUserQueryService queryService;
    
    private GetMyProfileInfoUseCase(IUserQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<MyProfileDetails> execute(GetMyProfileInfoRequestDTO request) throws GenericAppException {
        try {
            
            final UserDetails user = queryService.getUserById(request.subjectId);
            
            final MyProfileDetails myProfile = new MyProfileDetails(
                    user.id,
                    user.name,
                    user.email,
                    user.isEmailVerified
            );
            
            return Result.ok(myProfile);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
    }
    
    public static GetMyProfileInfoUseCase getInstance() {
        return GetMyProfileInfoUseCaseHolder.INSTANCE;
    }
    
    private static class GetMyProfileInfoUseCaseHolder {

        private static final GetMyProfileInfoUseCase INSTANCE =
                new GetMyProfileInfoUseCase(UserQueryService.getInstance());
    }
}
