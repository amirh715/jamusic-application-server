/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.KeyStoreManager;

import java.util.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Persistence.database.Models.KeyValue;

/**
 *
 * @author dada
 */
public class KeyStoreManager {
    
    private final EntityManagerFactoryHelper emfh;
    private Map<String, String> items;
    
    public void set(String key, String value) {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final KeyValue item = new KeyValue();
            item.setKey(key);
            item.setValue(value);
            
            em.persist(item);
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
        } finally {
            em.close();
        }

    }
    
    public String get(String key) {
        return items.get("key");
    }
    
    public void refresh() {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final List<KeyValue> refreshedItems =
                    em.createQuery("FROM KeyValue store")
                    .getResultList();
            items = new HashMap<>();
            refreshedItems
                    .forEach(item -> items.put(item.getKey(), item.getValue()));
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }

    }
    
    private KeyStoreManager(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    public static KeyStoreManager getInstance() {
        return KeyStoreManagerHolder.INSTANCE;
    }
    
    private static class KeyStoreManagerHolder {

        private static final KeyStoreManager INSTANCE =
                new KeyStoreManager(EntityManagerFactoryHelper.getInstance());
    }
}
