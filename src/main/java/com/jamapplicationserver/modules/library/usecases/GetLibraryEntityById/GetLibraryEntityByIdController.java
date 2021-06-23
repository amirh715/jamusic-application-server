/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetLibraryEntityById;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUseCase;

/**
 *
 * @author dada
 */
public class GetLibraryEntityByIdController extends BaseController {
    
    private final IUseCase usecase;
    
    private GetLibraryEntityByIdController(IUseCase usecase) {
        this.usecase = usecase;
    }

    @Override
    public void executeImpl() {
        
        
        
    }
    
    public static GetLibraryEntityByIdController getInstance() {
        return GetLibraryEntityByIdControllerHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityByIdControllerHolder {

        private static final GetLibraryEntityByIdController INSTANCE =
                new GetLibraryEntityByIdController(GetLibraryEntityByIdUseCase.getInstance());
    }
}
