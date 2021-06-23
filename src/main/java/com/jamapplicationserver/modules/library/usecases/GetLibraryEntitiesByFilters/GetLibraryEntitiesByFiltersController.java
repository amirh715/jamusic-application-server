/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetLibraryEntitiesByFilters;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntitiesByFiltersController extends BaseController {
    
    private final IUseCase usecase;
    
    private GetLibraryEntitiesByFiltersController(IUseCase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        
        
    }
    
    public static GetLibraryEntitiesByFiltersController getInstance() {
        return GetLibraryEntitiesByFiltersControllerHolder.INSTANCE;
    }
    
    private static class GetLibraryEntitiesByFiltersControllerHolder {

        private static final GetLibraryEntitiesByFiltersController INSTANCE =
                new GetLibraryEntitiesByFiltersController(GetLibraryEntitiesByFiltersUseCase.getInstance());
    }
}
