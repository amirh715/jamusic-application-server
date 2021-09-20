/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

/**
 *
 * @author amirhossein
 * 
 */
public abstract class Entity {
    
    public final UniqueEntityId id;
    
    private final DateTime createdAt;
    private DateTime lastModifiedAt;
    
    public Entity(
            UniqueEntityId id,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    
    public Entity() {
        this.id = new UniqueEntityId();
        this.createdAt = DateTime.createNow();
        this.lastModifiedAt = DateTime.createNow();
    }
    
    public final UniqueEntityId getId() {
        return this.id;
    }
    
    public final DateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public final DateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    protected final void modified() {
        this.lastModifiedAt = DateTime.createNow();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Entity))
            return false;
        Entity e = (Entity) obj;
        return e.id.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
    @Override
    public String toString() {
        return this.id.toString();
    }
    
}
