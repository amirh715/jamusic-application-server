/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetTrackAudioById;

/**
 *
 * @author dada
 */
public class GetTrackAudioByIdUseCase {
    
    private GetTrackAudioByIdUseCase() {
    }
    
    public static GetTrackAudioByIdUseCase getInstance() {
        return GetTrackAudioByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetTrackAudioByIdUseCaseHolder {

        private static final GetTrackAudioByIdUseCase INSTANCE = new GetTrackAudioByIdUseCase();
    }
}
