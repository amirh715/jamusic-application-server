/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityById;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntityByIdController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetLibraryEntityByIdController(IUsecase usecase) {
        this.usecase = usecase;
    }

    @Override
    public void executeImpl() {
        
        try {
            
            // ?full=[true || false]
            
            final Result result = this.usecase.execute(null);
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static GetLibraryEntityByIdController getInstance() {
        return GetLibraryEntityByIdControllerHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityByIdControllerHolder {

        private static final GetLibraryEntityByIdController INSTANCE =
                new GetLibraryEntityByIdController(GetLibraryEntityByIdUseCase.getInstance());
    }
}
