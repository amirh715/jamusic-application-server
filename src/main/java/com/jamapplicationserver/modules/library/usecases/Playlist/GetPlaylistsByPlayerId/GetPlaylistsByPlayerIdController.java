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
public class GetPlaylistsByPlayerIdController {
    
    private GetPlaylistsByPlayerIdController() {
    }
    
    public static GetPlaylistsByPlayerIdController getInstance() {
        return GetPlaylistsByPlayerIdHolder.INSTANCE;
    }
    
    private static class GetPlaylistsByPlayerIdHolder {

        private static final GetPlaylistsByPlayerIdController INSTANCE = new GetPlaylistsByPlayerIdController();
    }
}
