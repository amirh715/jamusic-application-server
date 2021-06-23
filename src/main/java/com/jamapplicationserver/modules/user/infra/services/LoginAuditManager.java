/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.services;

import com.jamapplicationserver.infra.Persistence.database.Models.LoginAuditModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserModel;
import javax.persistence.EntityManager;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.BusinessError;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import java.util.*;

/**
 *
 * @author amirhossein
 */
public class LoginAuditManager {
    
    private LoginAuditManager() {
    }
    
    public void audit(
            UniqueEntityID userId,
            boolean wasSuccessful,
            BusinessError failureReason,
            String ip,
            String platform,
            String os
    ) {
        
        try {
        
            final EntityManager em = getEntityManager();

            em.getTransaction().begin();

            final UserModel user = em.getReference(UserModel.class, userId.toValue());

            final LoginAuditModel audit = new LoginAuditModel();

            audit.setId(UUID.randomUUID());
            audit.setIpAddress(ip);
            audit.setWasSuccessful(wasSuccessful);
            audit.setFailureReason(wasSuccessful ? null : failureReason.message);
            audit.setAttemptedAt(DateTime.createNow().getValue());
            audit.setPlatform(platform);
            audit.setOs(os);
            audit.setUser(user);

            em.persist(audit);

            em.getTransaction().commit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public Set<LoginAuditModel> queryAudit(UniqueEntityID userId) {
        
        try {
            
            final EntityManager em = getEntityManager();
            
            em.getTransaction().begin();
            
            em.getTransaction().commit();
            
            return null;
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    private static EntityManager getEntityManager() {
       return EntityManagerFactoryHelper.getInstance().getEntityManager();
    }
    
    public static LoginAuditManager getInstance() {
        return LoginAuditManagerHolder.INSTANCE;
    }
    
    private static class LoginAuditManagerHolder {

        private static final LoginAuditManager INSTANCE = new LoginAuditManager();
    }
}
