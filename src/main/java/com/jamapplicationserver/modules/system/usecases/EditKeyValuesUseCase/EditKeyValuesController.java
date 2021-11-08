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
public class EditKeyValuesController {
    
    private EditKeyValuesController() {
    }
    
    public static EditKeyValuesController getInstance() {
        return EditKeyValuesControllerHolder.INSTANCE;
    }
    
    private static class EditKeyValuesControllerHolder {

        private static final EditKeyValuesController INSTANCE = new EditKeyValuesController();
    }
}
