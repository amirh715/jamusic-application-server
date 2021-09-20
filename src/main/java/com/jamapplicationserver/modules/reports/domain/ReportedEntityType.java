/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public enum ReportedEntityType {
    
    BAND("B", "گروه"),
    SINGER("S", "خواننده"),
    ALBUM("A", "آلبوم"),
    TRACK("T", "آهنگ");
    
    private final String value;
    private final String valueInPersian;
    
    private ReportedEntityType(
            String value,
            String valueInPersian
    ) {
        this.value = value;
        this.valueInPersian = valueInPersian;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getValueInPersian() {
        return this.valueInPersian;
    }
    
    public static final Result<ReportedEntityType> create(String value) {
        
        try {
            
            if(value == null) return Result.fail(new ValidationError("reported entity type is required"));
        
            final ReportedEntityType type = ReportedEntityType.valueOf(value);
            
            return Result.ok(type);
            
        } catch(IllegalArgumentException e) {
            return Result.fail(new ValidationError("Reported entity type is invalid"));
        }
        
    }
    
}
