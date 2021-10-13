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
    
    private final EntityManagerFactoryHelper emfh;
    private final Gson gson;
    
    public void auditRecord(
            Object entity,
            AuditAction action,
            String note
    ) {
        
        System.out.println("Commit mememememememe 1");
        
        final EntityManager em = emfh.createEntityManager();
        
        final EntityTransaction tnx = em.getTransaction();
        
        try {
        
        tnx.begin();
        
        final AuditTrailModel audit = new AuditTrailModel();

        audit.setId(UUID.randomUUID());
        audit.setTableName(entity.getClass().getSimpleName());
        audit.setAuditedAt(LocalDateTime.now());
        audit.setNote(note);
        audit.setRecord(stringify(entity));
        
        System.out.println("Commit mememememememe 2");
        
        em.persist(audit);
        
        System.out.println("Commit mememememememe 3");
        
        tnx.commit();
        
        System.out.println("Commit mememememememe 4");
        
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
    public List<AuditTrailModel> queryAudit() {
        
        return null;
    }
    
    private String stringify(Object entity) {
        return gson.toJson(entity);
    }
    
    private AuditManager(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
        this.gson = new GsonBuilder().create();
    }
    
    public static AuditManager getInstance() {
        return AuditManagerHolder.INSTANCE;
    }
    
    private static class AuditManagerHolder {

        private static final AuditManager INSTANCE =
                new AuditManager(EntityManagerFactoryHelper.getInstance());
    }
}
