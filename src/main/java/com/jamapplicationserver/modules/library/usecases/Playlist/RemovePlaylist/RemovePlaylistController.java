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
public class RemovePlaylistController {
    
    private RemovePlaylistController() {
    }
    
    public static RemovePlaylistController getInstance() {
        return RemovePlaylistControllerHolder.INSTANCE;
    }
    
    private static class RemovePlaylistControllerHolder {

        private static final RemovePlaylistController INSTANCE = new RemovePlaylistController();
    }
}
