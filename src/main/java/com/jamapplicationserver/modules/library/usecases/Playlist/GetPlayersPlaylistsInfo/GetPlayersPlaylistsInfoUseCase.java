/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlayersPlaylistsInfo;

/**
 *
 * @author dada
 */
public class GetPlayersPlaylistsInfoUseCase {
    
    private GetPlayersPlaylistsInfoUseCase() {
    }
    
    public static GetPlayersPlaylistsInfoUseCase getInstance() {
        return GetPlayersPlaylistsInfoUseCaseHolder.INSTANCE;
    }
    
    private static class GetPlayersPlaylistsInfoUseCaseHolder {

        private static final GetPlayersPlaylistsInfoUseCase INSTANCE = new GetPlayersPlaylistsInfoUseCase();
    }
}
