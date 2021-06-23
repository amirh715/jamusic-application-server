/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetAllGenres;

import com.jamapplicationserver.core.infra.BaseController;

/**
 *
 * @author dada
 */
public class GetAllGenresController extends BaseController {
    
    private GetAllGenresController() {
    }
    
    @Override
    public void executeImpl() {
        
    }
    
    public static GetAllGenresController getInstance() {
        return GetAllGenresControllerHolder.INSTANCE;
    }
    
    private static class GetAllGenresControllerHolder {

        private static final GetAllGenresController INSTANCE = new GetAllGenresController();
    }
}
