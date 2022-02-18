/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetMyProfileInfo;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.modules.user.infra.DTOs.commands.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.MyProfileDetails;

/**
 *
 * @author dada
 */
public class GetMyProfileInfoController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetMyProfileInfoController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final GetMyProfileInfoRequestDTO dto =
                    new GetMyProfileInfoRequestDTO(
                            subjectId,
                            subjectRole
                    );
            
            final Result<MyProfileDetails> result = usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                return;
            }
            
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetMyProfileInfoController getInstance() {
        return GetMyProfileInfoControllerHolder.INSTANCE;
    }
    
    private static class GetMyProfileInfoControllerHolder {

        private static final GetMyProfileInfoController INSTANCE =
                new GetMyProfileInfoController(GetMyProfileInfoUseCase.getInstance());
    }
}
