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
    
    public boolean equalsTo(Identifier<T> id) {
        if(id == null) return false;
        if(id.getClass() == this.getClass()) return false;
        return this.value == id;
    }
    
    public T toValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }
    
}
