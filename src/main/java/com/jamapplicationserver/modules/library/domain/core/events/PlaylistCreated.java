/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.events;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.modules.library.domain.Player.Player;

/**
 *
 * @author dada
 */
public class PlaylistCreated extends DomainEvent {
    
    public final Playlist playlist;
    public final Player player;
    
    public PlaylistCreated(Playlist playlist, Player player) {
        super(playlist.getId());
        this.playlist = playlist;
        this.player = player;
    }
    
}
