/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.repository;

import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;

/**
 *
 * @author dada
 */
public class ShowcaseRepository implements IShowcaseRepository {
    
    private final EntityManagerFactoryHelper emfh;
    private final long MAX_ALLOWED_SHOWCASES = 15;
    
    private ShowcaseRepository(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    @Override
    public final Set<Showcase> fetchAll() {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {

            return
                    em.createQuery("SELECT showcase FROM ShowcaseModel showcase", ShowcaseModel.class)
                    .getResultStream()
                    .map(showcase -> ShowcaseMapper.toDomain(showcase))
                    .collect(Collectors.toSet());

        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public final Showcase fetchByIndex(int index) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final ShowcaseModel result =
                    (ShowcaseModel) em.createQuery("SELECT showcase FROM ShowcaseModel showcase WHERE showcase.index = ?1")
                    .setParameter(1, index)
                    .getSingleResult();
            
            return ShowcaseMapper.toDomain(result);
        }  catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public final Showcase fetchById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final ShowcaseModel result =
                    (ShowcaseModel) em.createQuery("SELECT showcase FROM ShowcaseModel showcase WHERE showcase.id = ?1")
                    .setParameter(1, id.toValue())
                    .getSingleResult();
            
            return ShowcaseMapper.toDomain(result);
        } catch(NoResultException e) {
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public final void save(Showcase entity) throws Exception {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        ShowcaseModel model;
        
        try {

            tnx.begin();
            
            model = ShowcaseMapper.toPersistence(entity);
            
            final UserModel creator = em.find(UserModel.class, entity.getCreatorId().toValue());
            
            model.setCreator(creator);
            
            if(exists(entity.id)) { // update existing entity
                
                em.remove(model);
                
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
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public final void remove(Showcase entity) {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final ShowcaseModel model =
                    em.find(ShowcaseModel.class, entity.getId().toValue());
            
            em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public final boolean exists(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            ShowcaseModel model =
                    em.find(ShowcaseModel.class, id.toValue());
            
            return model != null;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    public static ShowcaseRepository getInstance() {
        return ShowcaseRepositoryHolder.INSTANCE;
    }
    
    private static class ShowcaseRepositoryHolder {

        private static final ShowcaseRepository INSTANCE =
                new ShowcaseRepository(
                        EntityManagerFactoryHelper.getInstance()
                );
    }
}
