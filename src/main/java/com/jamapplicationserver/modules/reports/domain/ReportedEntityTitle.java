/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class ReportedEntityTitle extends ValueObject<String> {
    
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;
    
    private final String value;
    
    private ReportedEntityTitle(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<ReportedEntityTitle> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError());
        
        if(
                value.length() < MIN_LENGTH ||
                value.length() > MAX_LENGTH
        ) return Result.fail(new ValidationError());
        
        return Result.ok(new ReportedEntityTitle(value));
    }
    
}
