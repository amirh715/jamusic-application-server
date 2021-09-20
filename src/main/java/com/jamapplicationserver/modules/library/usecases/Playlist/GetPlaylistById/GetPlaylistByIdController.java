/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Playlist.GetPlaylistById;

/**
 *
 * @author dada
 */
public class GetPlaylistByIdController {
    
    private GetPlaylistByIdController() {
    }
    
    public static GetPlaylistByIdController getInstance() {
        return GetPlaylistByIdControllerHolder.INSTANCE;
    }
    
    private static class GetPlaylistByIdControllerHolder {

        private static final GetPlaylistByIdController INSTANCE = new GetPlaylistByIdController();
    }
}
