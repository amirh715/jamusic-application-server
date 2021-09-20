/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.Player.*;

/**
 *
 * @author dada
 */
public interface IPlayerRepository {
    
    public Player fetchById(UniqueEntityId id);
    
    public boolean exists(UniqueEntityId id);
    
    public Set<Player> fetchByFilters(PlayerFilters filters);
    
    public void save(Player player);
    
}
