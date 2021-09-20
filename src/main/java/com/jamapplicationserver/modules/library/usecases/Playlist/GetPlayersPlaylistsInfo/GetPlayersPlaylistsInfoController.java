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
public class GetPlayersPlaylistsInfoController {
    
    private GetPlayersPlaylistsInfoController() {
    }
    
    public static GetPlayersPlaylistsInfoController getInstance() {
        return GetPlayersPlaylistsInfoHolder.INSTANCE;
    }
    
    private static class GetPlayersPlaylistsInfoHolder {

        private static final GetPlayersPlaylistsInfoController INSTANCE = new GetPlayersPlaylistsInfoController();
    }
}
