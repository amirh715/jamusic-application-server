/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database;

import javax.persistence.*;

/**
 *
 * @author amirhossein
 */
public class EntityManagerFactoryHelper {
    
    private final EntityManagerFactory factory;
    
    private EntityManagerFactoryHelper() {
        try {
            factory = Persistence.createEntityManagerFactory("manager1");
        } catch(Exception e) {
            throw e;
        }
    }
    
    public static EntityManagerFactoryHelper getInstance() {
        return EntityManagerFactoryHelperHolder.INSTANCE;
    }
    
    public final EntityManagerFactory getFactory() {
        return this.factory;
    }
    
    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
    
    private static class EntityManagerFactoryHelperHolder {

        private static final EntityManagerFactoryHelper INSTANCE = new EntityManagerFactoryHelper();
    }
}
