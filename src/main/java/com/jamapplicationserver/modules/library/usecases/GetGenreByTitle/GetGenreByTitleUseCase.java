/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetGenreByTitle;

/**
 *
 * @author dada
 */
public class GetGenreByTitleUseCase {
    
    private GetGenreByTitleUseCase() {
    }
    
    public static GetGenreByTitleUseCase getInstance() {
        return GetGenreByTitleUseCaseHolder.INSTANCE;
    }
    
    private static class GetGenreByTitleUseCaseHolder {

        private static final GetGenreByTitleUseCase INSTANCE = new GetGenreByTitleUseCase();
    }
}
