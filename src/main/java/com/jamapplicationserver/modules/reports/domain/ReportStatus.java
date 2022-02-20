/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import java.util.stream.Stream;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public enum ReportStatus {

    PENDING_ASSIGNMENT("PENDING_ASSIGNMENT", "ثبت شده"),
    ASSIGNED("ASSIGNED", "در انتظار پردازش"),
    PROCESSED("PROCESSED", "پردازش شده"),
    ARCHIVED("ARCHIVED", "آرشیو شده");
    
    private final String value;
    private final String valueInPersian;
    
    private ReportStatus(
        String value,
        String valueInPersian
    ) {
        this.value = value;
        this.valueInPersian = valueInPersian;
    }
    
    public static Stream<ReportStatus> stream() {
        return Stream.of(ReportStatus.values());
    }
    
    public static final Result<ReportStatus> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("وضعیت گزارش ضروری است"));
        final boolean isValid =
                ReportStatus.stream().anyMatch(status -> value.equals(status.getValue()));
        return isValid ? Result.ok(ReportStatus.valueOf(value)) : Result.fail(new ValidationError("مقدار وضعیت گزارش درست نیست"));
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getValueInPersian() {
        return this.valueInPersian;
    }
    
    public boolean isAssigned() {
        return this.equals(ReportStatus.ASSIGNED);
    }
    
    public boolean isProcessed() {
        return this.equals(ReportStatus.PROCESSED);
    }
    
    public boolean isPendingAssignment() {
        return this.equals(ReportStatus.PENDING_ASSIGNMENT);
    }
    
    public boolean isArchived() {
        return this.equals(ReportStatus.ARCHIVED);
    }
    

}
