/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.RemoveGenre;

import com.jamapplicationserver.core.infra.BaseController;

/**
 *
 * @author dada
 */
public class RemoveGenreController extends BaseController {
    
    private RemoveGenreController() {
    }
    
    @Override
    public void executeImpl() {
        
    }
    
    public static RemoveGenreController getInstance() {
        return RemoveGenreControllerHolder.INSTANCE;
    }
    
    private static class RemoveGenreControllerHolder {

        private static final RemoveGenreController INSTANCE = new RemoveGenreController();
    }
}
