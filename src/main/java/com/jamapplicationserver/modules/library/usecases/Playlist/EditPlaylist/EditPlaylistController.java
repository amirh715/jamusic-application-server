/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.EditPlaylist;

/**
 *
 * @author dada
 */
public class EditPlaylistController {
    
    private EditPlaylistController() {
    }
    
    public static EditPlaylistController getInstance() {
        return EditPlaylistControlleHolder.INSTANCE;
    }
    
    private static class EditPlaylistControlleHolder {

        private static final EditPlaylistController INSTANCE = new EditPlaylistController();
    }
}
