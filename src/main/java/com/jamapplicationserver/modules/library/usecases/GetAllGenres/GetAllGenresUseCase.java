/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetAllGenres;

/**
 *
 * @author dada
 */
public class GetAllGenresUseCase {
    
    private GetAllGenresUseCase() {
    }
    
    public static GetAllGenresUseCase getInstance() {
        return GetAllGenresUseCaseHolder.INSTANCE;
    }
    
    private static class GetAllGenresUseCaseHolder {

        private static final GetAllGenresUseCase INSTANCE = new GetAllGenresUseCase();
    }
}
