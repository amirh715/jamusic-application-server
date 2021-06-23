/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetLibraryEntityById;

import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetLibraryEntityByIdUseCase implements IUseCase<String, String> {
    
    private GetLibraryEntityByIdUseCase() {
    }
    
    @Override
    public Result execute(String request) throws GenericAppException {
        
        try {
            
            return Result.ok();
            
        } catch(Exception e) {
            return Result.ok();
        }
        
    }
    
    public static GetLibraryEntityByIdUseCase getInstance() {
        return GetLibraryEntityByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetLibraryEntityByIdUseCaseHolder {

        private static final GetLibraryEntityByIdUseCase INSTANCE = new GetLibraryEntityByIdUseCase();
    }
}
