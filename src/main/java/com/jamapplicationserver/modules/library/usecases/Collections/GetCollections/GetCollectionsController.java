/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Collections.GetCollections;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class GetCollectionsController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetCollectionsController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static GetCollectionsController getInstance() {
        return GetCollectionsControllerHolder.INSTANCE;
    }
    
    private static class GetCollectionsControllerHolder {

        private static final GetCollectionsController INSTANCE =
                new GetCollectionsController(GetCollectionsUseCase.getInstance());
    }
}
