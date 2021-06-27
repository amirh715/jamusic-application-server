/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetGenreById;

import com.jamapplicationserver.core.infra.BaseController;

/**
 *
 * @author dada
 */
public class GetGenreByIdController extends BaseController {
    
    private GetGenreByIdController() {
    }
    
    @Override
    public void executeImpl() {
        
        
        
    }
    
    public static GetGenreByIdController getInstance() {
        return GetGenreByIdControllerHolder.INSTANCE;
    }
    
    private static class GetGenreByIdControllerHolder {

        private static final GetGenreByIdController INSTANCE = new GetGenreByIdController();
    }
}
