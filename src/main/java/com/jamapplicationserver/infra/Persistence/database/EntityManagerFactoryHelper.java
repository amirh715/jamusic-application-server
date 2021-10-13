/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database;

import java.util.*;
import javax.persistence.*;
import org.hibernate.*;

/**
 *
 * @author amirhossein
 */
public class EntityManagerFactoryHelper {
    
    private final EntityManagerFactory factory;
    
    private EntityManagerFactoryHelper() {
        try {
            
            final Map<String, Object> configuration = new HashMap<>();
            
            factory = Persistence.createEntityManagerFactory("manager1");
            
        } catch(Exception e) {
            throw e;
        }
    }
    
    public static EntityManagerFactoryHelper getInstance() {
        return EntityManagerFactoryHelperHolder.INSTANCE;
    }
    
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }
    
    private static class EntityManagerFactoryHelperHolder {

        private static final EntityManagerFactoryHelper INSTANCE =
                new EntityManagerFactoryHelper();
    }
}
