/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Interceptors;

import org.hibernate.EmptyInterceptor;
import java.io.Serializable;
import org.hibernate.type.Type;
import java.time.LocalDateTime;
import java.util.UUID;
import com.google.gson.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author amirhossein
 */
public class RecordAuditInterceptor extends EmptyInterceptor {
    
    private static final Gson gson =
            new GsonBuilder()
            .create();
    
    private static final String stringify(Object entity) {
        return gson.toJson(entity);
    }

    @Override
    public boolean onSave(
            Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types
    ) {
        
        final AuditTrailModel audit = new AuditTrailModel();
        
        audit.setId(UUID.randomUUID());
        audit.setTableName(entity.getClass().getSimpleName());
        audit.setAuditedAt(LocalDateTime.now());
        audit.setNote("This is an audit trail.");
        audit.setAction(AuditActionEnum.U);
        
        final String record = stringify(entity);
        audit.setRecord(record);
        
        return true;
    }
    
}
