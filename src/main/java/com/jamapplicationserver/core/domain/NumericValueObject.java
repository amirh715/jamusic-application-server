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
public abstract class NumericValueObject<T extends Number> extends ValueObject<T> {

    protected final T value;
    
    public NumericValueObject(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return this.value;
    }
    
    public final String toPersian() {
        
        return "";
    }
    
}
