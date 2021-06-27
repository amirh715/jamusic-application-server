/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetGenreById;

/**
 *
 * @author dada
 */
public class GetGenreByIdUseCase {
    
    private GetGenreByIdUseCase() {
    }
    
    public static GetGenreByIdUseCase getInstance() {
        return GetGenreByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetGenreByIdUseCaseHolder {

        private static final GetGenreByIdUseCase INSTANCE = new GetGenreByIdUseCase();
    }
}
