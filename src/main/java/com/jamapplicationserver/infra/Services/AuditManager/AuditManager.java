/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.AuditManager;

import java.util.*;
import javax.persistence.*;
import com.google.gson.*;
import com.jamapplicationserver.infra.Persistence.database.Models.AuditTrailModel;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import java.time.LocalDateTime;

/**
 *
 * @author amirhossein
 */
public class AuditManager {
    
    private final EntityManagerFactoryHelper em;
    private final Gson gson;
    
    public void auditRecord(
            AggregateRoot aggregate,
            AuditAction action,
            DateTime auditedAt
    ) {
        
        final EntityManager em = getEntityManager();
        
        em.getTransaction().begin();
        
        final AuditTrailModel audit = new AuditTrailModel();

        audit.setId(UUID.randomUUID());
        audit.setTableName(aggregate.getClass().getName());
        audit.setAuditedAt(LocalDateTime.now());
        audit.setNote("This is an audit.");

        audit.setRecord(stringify(aggregate));
        
        
    }
    
    public List<AuditTrailModel> queryAudit() {
        
        return null;
    }
    
    private String stringify(AggregateRoot aggregate) {
        return this.gson.toJson(aggregate);
    }
    
    private AuditManager(EntityManagerFactoryHelper em) {
        this.em = em;
        this.gson = new GsonBuilder().create();
    }
    
    public EntityManager getEntityManager() {
        return this.em.getEntityManager();
    }
    
    public static AuditManager getInstance() {
        return AuditManagerHolder.INSTANCE;
    }
    
    private static class AuditManagerHolder {

        private static final AuditManager INSTANCE =
                new AuditManager(EntityManagerFactoryHelper.getInstance());
    }
}
