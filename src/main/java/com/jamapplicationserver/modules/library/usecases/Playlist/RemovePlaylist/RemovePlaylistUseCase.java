/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.RemovePlaylist;

/**
 *
 * @author dada
 */
public class RemovePlaylistUseCase {
    
    private RemovePlaylistUseCase() {
    }
    
    public static RemovePlaylistUseCase getInstance() {
        return RemovePlaylistUseCaseHolder.INSTANCE;
    }
    
    private static class RemovePlaylistUseCaseHolder {

        private static final RemovePlaylistUseCase INSTANCE = new RemovePlaylistUseCase();
    }
}
