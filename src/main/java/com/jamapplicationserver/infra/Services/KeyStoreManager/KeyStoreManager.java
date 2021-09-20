/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.KeyStoreManager;

import java.util.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Persistence.database.Models.KeyValueStore;

/**
 *
 * @author dada
 */
public class KeyStoreManager {
    
    private final EntityManager em;
    private Map<String, String> items;
    
    public void set(String key, String value) {
        final EntityTransaction tnx = em.getTransaction();
        final KeyValueStore item = new KeyValueStore();
        item.setKey(key);
        item.setValue(value);
        tnx.begin();
        em.persist(item);
        tnx.commit();
    }
    
    public String get(String key) {
        return items.get("key");
    }
    
    public void refresh() {
        final List<KeyValueStore> refreshedItems =
                em.createQuery("FROM KeyStore store")
                .getResultList();
        this.items = new HashMap<>();
        refreshedItems
                .forEach(item -> this.items.put(item.getKey(), item.getValue()));
    }
    
    private KeyStoreManager(EntityManager em) {
        this.em = em;
    }
    
    public static KeyStoreManager getInstance() {
        return KeyStoreManagerHolder.INSTANCE;
    }
    
    private static class KeyStoreManagerHolder {

        private static final KeyStoreManager INSTANCE =
                new KeyStoreManager(EntityManagerFactoryHelper.getInstance().getEntityManager());
    }
}
