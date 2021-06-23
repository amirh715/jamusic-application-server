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
public abstract class ValueObject<T> {
    
    public boolean equalsTo(T vo) {
        if(this == vo) return true;
        if(vo == null || getClass() != vo.getClass()) return false;
        T that = (T) vo;
        return that.equals(this);
    }
    
    public abstract T getValue();
    
}
