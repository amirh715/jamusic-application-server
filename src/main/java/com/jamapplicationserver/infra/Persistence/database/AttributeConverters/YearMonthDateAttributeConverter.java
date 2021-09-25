/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.AttributeConverters;

import java.time.*;
import javax.persistence.AttributeConverter;
import java.sql.Date;

/**
 *
 * @author dada
 */
public class YearMonthDateAttributeConverter implements AttributeConverter<YearMonth, Date> {
    
    @Override
    public YearMonth convertToEntityAttribute(Date date) {
        return date != null ?
                YearMonth.from(
                        Instant.ofEpochMilli(date.getTime())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                ) : null;
    }
    
    @Override
    public Date convertToDatabaseColumn(YearMonth yearMonth) {
        return yearMonth != null ? Date.valueOf(yearMonth.atDay(1)) : null;
    }
    
}
