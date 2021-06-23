/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.EditGenre;

/**
 *
 * @author dada
 */
public class EditGenreUseCase {
    
    private EditGenreUseCase() {
    }
    
    public static EditGenreUseCase getInstance() {
        return EditGenreUseCaseHolder.INSTANCE;
    }
    
    private static class EditGenreUseCaseHolder {

        private static final EditGenreUseCase INSTANCE = new EditGenreUseCase();
    }
}
