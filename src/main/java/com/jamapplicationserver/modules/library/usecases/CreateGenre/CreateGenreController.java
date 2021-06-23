/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.CreateGenre;

import com.jamapplicationserver.core.infra.BaseController;

/**
 *
 * @author dada
 */
public class CreateGenreController extends BaseController {
    
    private CreateGenreController() {
    }
    
    @Override
    public void executeImpl() {
        
    }
    
    public static CreateGenreController getInstance() {
        return CreateGenreControllerHolder.INSTANCE;
    }
    
    private static class CreateGenreControllerHolder {

        private static final CreateGenreController INSTANCE = new CreateGenreController();
    }
}
