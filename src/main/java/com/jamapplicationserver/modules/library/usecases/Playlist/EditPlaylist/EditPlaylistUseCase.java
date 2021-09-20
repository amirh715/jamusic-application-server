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
public class EditPlaylistUseCase {
    
    private EditPlaylistUseCase() {
    }
    
    public static EditPlaylistUseCase getInstance() {
        return EditPlaylistUseCaseHolder.INSTANCE;
    }
    
    private static class EditPlaylistUseCaseHolder {

        private static final EditPlaylistUseCase INSTANCE = new EditPlaylistUseCase();
    }
}
