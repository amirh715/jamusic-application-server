/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.RemoveAllPlaylistsByPlayerId;

/**
 *
 * @author dada
 */
public class RemoveAllPlaylistsByPlayerIdUseCase {
    
    private RemoveAllPlaylistsByPlayerIdUseCase() {
    }
    
    public static RemoveAllPlaylistsByPlayerIdUseCase getInstance() {
        return RemoveAllPlaylistsByPlayerIdUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveAllPlaylistsByPlayerIdUseCaseHolder {

        private static final RemoveAllPlaylistsByPlayerIdUseCase INSTANCE = new RemoveAllPlaylistsByPlayerIdUseCase();
    }
}
