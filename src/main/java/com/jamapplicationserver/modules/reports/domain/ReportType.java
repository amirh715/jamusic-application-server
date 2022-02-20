/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import java.util.stream.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public enum ReportType {
    
    CONTENT("CONTENT"), TECHNICAL("TECHNICAL");
    
    private final String value;
    
    private ReportType(String value) {
        this.value = value;
    }
    
    public static Stream<ReportType> stream() {
        return Stream.of(ReportType.values());
    }
    
    public static final Result<ReportType> create(String value) {
        
        if(value == null)
            return Result.fail(new ValidationError("نوع گزارش ضروری است"));
        
        final boolean isValid =
                ReportType.stream().anyMatch(type -> value.equals(type.getValue()));
        return isValid ?
                Result.ok(ReportType.valueOf(value)) :
                Result.fail(new ValidationError("مقدار نوع گزارش درست نیست"));
    }
    
    public String getValue() {
        return this.value;
    }
    
    public boolean isTechnical() {
        return this.equals(ReportType.TECHNICAL);
    }
    
    public boolean isContentRelated() {
        return this.equals(ReportType.CONTENT);
    }
    
}
