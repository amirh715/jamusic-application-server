/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.CreateGenre;

/**
 *
 * @author dada
 */
public class CreateGenreUseCase {
    
    private CreateGenreUseCase() {
    }
    
    public static CreateGenreUseCase getInstance() {
        return CreateGenreUseCaseHolder.INSTANCE;
    }
    
    private static class CreateGenreUseCaseHolder {

        private static final CreateGenreUseCase INSTANCE = new CreateGenreUseCase();
    }
}
