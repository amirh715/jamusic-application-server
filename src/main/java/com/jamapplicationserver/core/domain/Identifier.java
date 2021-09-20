/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

/**
 *
 * @author amirhossein
 * @param <T>
 */
public class Identifier<T> {
    
    protected final T value;
    
    public Identifier(T value) {
        this.value = value;
    }
    
    @Override
    public boolean equals(Object id) {
        if(id == this)
            return true;
        if(!(id instanceof Identifier))
            return false;
        Identifier i = (Identifier) id;
        return i.value.equals(id);
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    public T toValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }
    
}
