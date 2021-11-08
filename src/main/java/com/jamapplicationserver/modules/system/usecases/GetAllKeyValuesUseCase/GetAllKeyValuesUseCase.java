/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.system.usecases.GetAllKeyValuesUseCase;

/**
 *
 * @author dada
 */
public class GetAllKeyValuesUseCase {
    
    private GetAllKeyValuesUseCase() {
    }
    
    public static GetAllKeyValuesUseCase getInstance() {
        return GetAllKeyValuesUseCaseHolder.INSTANCE;
    }
    
    private static class GetAllKeyValuesUseCaseHolder {

        private static final GetAllKeyValuesUseCase INSTANCE = new GetAllKeyValuesUseCase();
    }
}
