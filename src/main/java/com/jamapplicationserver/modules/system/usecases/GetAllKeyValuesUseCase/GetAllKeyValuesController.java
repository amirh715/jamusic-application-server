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
public class GetAllKeyValuesController {
    
    private GetAllKeyValuesController() {
    }
    
    public static GetAllKeyValuesController getInstance() {
        return GetAllKeyValuesControllerHolder.INSTANCE;
    }
    
    private static class GetAllKeyValuesControllerHolder {

        private static final GetAllKeyValuesController INSTANCE = new GetAllKeyValuesController();
    }
}
