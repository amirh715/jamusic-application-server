/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import com.jamapplicationserver.core.domain.NumericValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class Rate extends NumericValueObject<Double> {

    private Rate(double value) {
        super(value);
    }
    
    @Override
    public Double getValue() {
        return this.value;
    }
    
    public static final Result<Rate> create(double value) {
        
        if(value > 5 && value < 0) return Result.fail(new ValidationError());
        
        return Result.ok(new Rate(value));
    }
    
    public static final Result<Rate> create(long playedCount, long totalPlayedCount) {
        
        

        return Result.ok(new Rate(5));
    }
    
    public static final Rate createZero() {
        return new Rate(0);
    }
    
}
