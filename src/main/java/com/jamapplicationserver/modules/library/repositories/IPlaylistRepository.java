/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public interface IPlaylistRepository {
    
    public Playlist fetchById(UniqueEntityId id);
    
    public Set<Playlist> fetchByPlayerId(UniqueEntityId playerId);
    
    public void save(Playlist playlist);
    
    public void remove(Playlist playlist);
    
    public boolean exists(UniqueEntityId id);
    
    public Playlist toDomain(PlaylistModel model);
    
    public PlaylistModel toPersistence(Playlist playlist);
    
}
