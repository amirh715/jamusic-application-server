/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.repository;

import java.util.*;
import javax.persistence.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author dada
 */
public class ShowcaseRepository implements IShowcaseRepository {
    
    private final EntityManager em;
    private final long MAX_ALLOWED_SHOWCASES = 3;
    
    private ShowcaseRepository(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public final Set<Showcase> fetchAll() {
        
        try {
            
            final List<ShowcaseModel> results =
                    em.createQuery("SELECT showcase FROM ShowcaseModel showcase")
                    .getResultList();
            
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public final Showcase fetchByIndex(int index) {
        
        try {
            
            final ShowcaseModel result =
                    (ShowcaseModel) em.createQuery("SELECT showcase FROM ShowcaseModel showcase WHERE showcase.index = ?1")
                    .setParameter(1, index)
                    .getSingleResult();
            
            return ShowcaseMapper.toDomain(result);
        }  catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public final Showcase fetchById(UniqueEntityId id) {
        
        try {
            
            final ShowcaseModel result =
                    (ShowcaseModel) em.createQuery("SELECT showcase FROM ShowcaseModel showcase WHERE showcase.id = ?1")
                    .setParameter(1, id.toValue())
                    .getSingleResult();
            
            return ShowcaseMapper.toDomain(result);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public final void save(Showcase entity) {
        
        final EntityTransaction tnx = em.getTransaction();
        
        ShowcaseModel model;
        
        try {
            
            final boolean exists = this.exists(entity.id);
            
            tnx.begin();
            
            model = ShowcaseMapper.toPersistence(entity);
            
            final UserModel creator = em.find(UserModel.class, entity.getCreatorId().toValue());
            
            model.setCreator(creator);
            
            if(exists) { // update existing entity
                
                em.merge(model);
                
            } else { // insert new entity
                
                final long count =
                        em.createQuery("SELECT COUNT(showcase) FROM ShowcaseModel showcase")
                        .getFirstResult();
                if(count >= MAX_ALLOWED_SHOWCASES) throw new Exception("");
                
                em.persist(model);
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
        }
        
    }
    
    @Override
    public final void remove(Showcase entity) {
        
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final ShowcaseModel model = ShowcaseMapper.toPersistence(entity);
            
            em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
        }
        
    }
    
    @Override
    public final boolean exists(UniqueEntityId id) {
        
        try {
            
            ShowcaseModel model =
                    em.find(ShowcaseModel.class, id.toValue());
            
            return model != null;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public static ShowcaseRepository getInstance() {
        return ShowcaseRepositoryHolder.INSTANCE;
    }
    
    private static class ShowcaseRepositoryHolder {

        private static final ShowcaseRepository INSTANCE =
                new ShowcaseRepository(
                        EntityManagerFactoryHelper
                                .getInstance()
                                .getEntityManager()
                );
    }
}
