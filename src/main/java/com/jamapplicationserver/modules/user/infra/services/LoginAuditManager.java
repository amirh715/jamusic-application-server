/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.services;

import com.jamapplicationserver.infra.Persistence.database.Models.LoginAuditModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserModel;
import javax.persistence.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.BusinessError;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import java.util.*;

/**
 *
 * @author amirhossein
 */
public class LoginAuditManager {
    
    final EntityManagerFactoryHelper emfh;
    
    private LoginAuditManager(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    public void audit(
            UniqueEntityId userId,
            boolean wasSuccessful,
            BusinessError failureReason,
            String ip,
            String platform,
            String os
    ) {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {

            tnx.begin();

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

            tnx.commit();
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
    public Set<LoginAuditModel> queryAudit(UniqueEntityId userId) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {

            
            return null;
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw e;
        }
        
    }
    
    public static LoginAuditManager getInstance() {
        return LoginAuditManagerHolder.INSTANCE;
    }
    
    private static class LoginAuditManagerHolder {

        private static final LoginAuditManager INSTANCE =
                new LoginAuditManager(EntityManagerFactoryHelper.getInstance());
    }
}
