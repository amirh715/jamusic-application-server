/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.system.Jobs;

import org.quartz.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Services.KeyStoreManager.KeyStoreManager;

/**
 *
 * @author dada
 */
public class KeyStoreRefreshJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) {
        
        try {
            
            final EntityManager em  =
                    EntityManagerFactoryHelper.getInstance().getEntityManager();
            
            KeyStoreManager.getInstance().refresh();
            
        } catch(Exception e) {
            
        }
        
    }
    
}
