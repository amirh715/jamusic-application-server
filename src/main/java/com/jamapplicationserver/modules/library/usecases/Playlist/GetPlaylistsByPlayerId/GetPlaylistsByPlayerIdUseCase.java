/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlaylistsByPlayerId;

/**
 *
 * @author dada
 */
public class GetPlaylistsByPlayerIdUseCase {
    
    private GetPlaylistsByPlayerIdUseCase() {
    }
    
    public static GetPlaylistsByPlayerIdUseCase getInstance() {
        return GetPlaylistsByPlayerIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetPlaylistsByPlayerIdUseCaseHolder {

        private static final GetPlaylistsByPlayerIdUseCase INSTANCE = new GetPlaylistsByPlayerIdUseCase();
    }
}
