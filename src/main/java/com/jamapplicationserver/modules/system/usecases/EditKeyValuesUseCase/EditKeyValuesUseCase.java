/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.system.usecases.EditKeyValuesUseCase;

/**
 *
 * @author dada
 */
public class EditKeyValuesUseCase {
    
    private EditKeyValuesUseCase() {
    }
    
    public static EditKeyValuesUseCase getInstance() {
        return EditKeyValuesUseCaseHolder.INSTANCE;
    }
    
    private static class EditKeyValuesUseCaseHolder {

        private static final EditKeyValuesUseCase INSTANCE = new EditKeyValuesUseCase();
    }
}
