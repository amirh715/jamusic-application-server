/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

/**
 *
 * @author amirhossein
 */
public abstract class NumericValueObject<T extends Number> extends ValueObject<T> {

    private final Number value;
    
    public NumericValueObject(Number value) {
        this.value = value;
    }

    
    public final String toPersian() {
        
        final String convertedToPersian = "";

        
        
        return convertedToPersian;
    }
    
}
