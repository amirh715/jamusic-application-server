/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.util.UUID;

/**
 *
 * @author amirhossein
 * @param <U>
 * 
 */
public abstract class Entity {
    
    public final UniqueEntityID id;
    
    public Entity(UniqueEntityID id) {
        this.id = id;
    }
    
    public Entity() {
        this.id = new UniqueEntityID();
    }
    
    public boolean equalsTo(Entity entity) {
        
        if(entity == null) return false;
        
        if(this == entity) return true;

        return this.id.equals(entity.id);
        
    }
    
    @Override
    public String toString() {
        return this.id.toString();
    }
    
}
