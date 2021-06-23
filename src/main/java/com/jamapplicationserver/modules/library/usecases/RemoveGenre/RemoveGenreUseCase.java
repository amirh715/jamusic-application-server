/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.RemoveGenre;

/**
 *
 * @author dada
 */
public class RemoveGenreUseCase {
    
    private RemoveGenreUseCase() {
    }
    
    public static RemoveGenreUseCase getInstance() {
        return RemoveGenreUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveGenreUseCaseHolder {

        private static final RemoveGenreUseCase INSTANCE = new RemoveGenreUseCase();
    }
}
