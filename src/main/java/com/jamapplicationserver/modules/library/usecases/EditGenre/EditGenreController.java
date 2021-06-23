/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.EditGenre;

import com.jamapplicationserver.core.infra.BaseController;

/**
 *
 * @author dada
 */
public class EditGenreController extends BaseController {
    
    private EditGenreController() {
    }
    
    @Override
    public void executeImpl() {
        
    }
    
    public static EditGenreController getInstance() {
        return EditGenreControllerHolder.INSTANCE;
    }
    
    private static class EditGenreControllerHolder {

        private static final EditGenreController INSTANCE = new EditGenreController();
    }
}
