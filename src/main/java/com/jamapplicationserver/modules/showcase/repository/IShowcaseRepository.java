/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.repository;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.showcase.domain.Showcase;

/**
 *
 * @author dada
 */
public interface IShowcaseRepository {
    
    public Set<Showcase> fetchAll();
    
    public Showcase fetchByIndex(int index);
    
    public Showcase fetchById(UniqueEntityId id);
    
    public boolean exists(UniqueEntityId id);
    
    public void save(Showcase showcase);
    
    public void remove(Showcase showcase);
    
}
