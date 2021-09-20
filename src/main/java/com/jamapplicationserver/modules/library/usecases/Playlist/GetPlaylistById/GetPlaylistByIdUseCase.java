/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlaylistById;

/**
 *
 * @author dada
 */
public class GetPlaylistByIdUseCase {
    
    private GetPlaylistByIdUseCase() {
    }
    
    public static GetPlaylistByIdUseCase getInstance() {
        return GetPlaylistByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetPlaylistByIdUseCaseHolder {

        private static final GetPlaylistByIdUseCase INSTANCE = new GetPlaylistByIdUseCase();
    }
}
