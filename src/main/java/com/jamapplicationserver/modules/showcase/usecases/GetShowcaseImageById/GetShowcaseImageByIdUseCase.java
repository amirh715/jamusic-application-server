/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetShowcaseImageById;

/**
 *
 * @author dada
 */
public class GetShowcaseImageByIdUseCase {
    
    private GetShowcaseImageByIdUseCase() {
    }
    
    public static GetShowcaseImageByIdUseCase getInstance() {
        return GetShowcaseImageByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetShowcaseImageByIdUseCaseHolder {

        private static final GetShowcaseImageByIdUseCase INSTANCE = new GetShowcaseImageByIdUseCase();
    }
}
