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
public class RemoveAllPlaylistsByPlayerIdController {
    
    private RemoveAllPlaylistsByPlayerIdController() {
    }
    
    public static RemoveAllPlaylistsByPlayerIdController getInstance() {
        return RemoveAllPlaylistsByPlayerIdControllerHolder.INSTANCE;
    }
    
    private static class RemoveAllPlaylistsByPlayerIdControllerHolder {

        private static final RemoveAllPlaylistsByPlayerIdController INSTANCE = new RemoveAllPlaylistsByPlayerIdController();
    }
}
