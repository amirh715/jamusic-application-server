/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.subscribers;

import java.util.*;
import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.events.*;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 */
public class AfterPlaylistCreated implements IDomainEventHandler<PlaylistCreated> {
    
    public AfterPlaylistCreated() {
    }
    
    @Override
    public void handleEvent(PlaylistCreated event) throws Exception {
                
        try {
            
            final IPlayerRepository repository =
                    PlayerRepository.getInstance();
            
            repository.save(event.player);
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public Class subscribedToEventType() {
        return PlaylistCreated.class;
    }
    
}
