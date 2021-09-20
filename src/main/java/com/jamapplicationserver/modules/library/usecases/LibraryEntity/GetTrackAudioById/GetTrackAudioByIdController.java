/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetTrackAudioById;

import com.jamapplicationserver.core.infra.BaseController;

/**
 *
 * @author dada
 */
public class GetTrackAudioByIdController extends BaseController {
    
    private GetTrackAudioByIdController() {
    }
    
    @Override
    public void executeImpl() {
        
    }
    
    public static GetTrackAudioByIdController getInstance() {
        return GetTrackAudioByIdHolder.INSTANCE;
    }
    
    private static class GetTrackAudioByIdHolder {

        private static final GetTrackAudioByIdController INSTANCE = new GetTrackAudioByIdController();
    }
}
