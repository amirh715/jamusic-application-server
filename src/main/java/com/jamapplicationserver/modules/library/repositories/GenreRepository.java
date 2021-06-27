/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import javax.persistence.*;
import javax.persistence.criteria.*;
import org.hibernate.exception.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;

/**
 *
 * @author dada
 */
public class GenreRepository implements IGenreRepository {
    
    private final EntityManager em;
    
    private static final int MAX_ALLOWED_GENRES = 50;
    
    private GenreRepository(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public Set<Genre> fetchAll() {
        
        try {
            
            final String query = "SELECT g FROM GenreModel g";
            
            return (Set<Genre>) this.em.createQuery(query)
                    .getResultStream()
                    .map(model -> GenreMapper.toDomain((GenreModel)model))
                    .collect(Collectors.toSet());

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    
    @Override
    public Genre fetchById(UniqueEntityID id) {
        
        try {
            
            final GenreModel model = this.em.find(GenreModel.class, id.toValue());
            
            return GenreMapper.toDomain(model);
            
        } catch(NoResultException e) {
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Genre fetchByTitle(GenreTitle title) {
        
        try {
            
            final String query = "FROM GenreModel g WHERE g.title = :title";

            final GenreModel model = (GenreModel) this.em.createQuery(query)
                    .setParameter("title", title.getValue())
                    .getSingleResult();
            
            return GenreMapper.toDomain(model);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void save(Genre genre) throws ConstraintViolationException {
        
        GenreModel model;
        
        final EntityTransaction tnx = this.em.getTransaction();
        
        try {
            
            tnx.begin();
        
            if(this.exists(genre.id)) { // updater existing genre

                model = new GenreModel();

                GenreMapper.mergeForPersistence(genre, model);

                em.merge(model);

            } else { // insert new genre

                model = GenreMapper.toPersistence(genre);

                em.persist(model);

            }
            
            tnx.commit();
            
        } catch(RollbackException e) {
            
            if(e.getClass() != null && e.getCause().getCause() != null) {
                
                final ConstraintViolationException exception = (ConstraintViolationException) e.getCause().getCause();
                
                final String constraintName = exception.getConstraintName();
                String message = "Unknown constraint violation";
                if(constraintName.equals("title_unique_key"))
                    message = "عنوان سبک تکراری است";
                if(constraintName.equals("title_in_persian_unique_key"))
                    message = "عنوان فارسی سبک تکراری است";
                throw new ConstraintViolationException(message, exception.getSQLException(), constraintName);
            }
            
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityID id) {
        
        try {
            
            final GenreModel model = this.em.find(GenreModel.class, id.toValue());
            return model != null;
            
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @Override
    public void remove(Genre genre) {

        try {
            
            final EntityTransaction tnx = this.em.getTransaction();
            
            final GenreModel model = GenreMapper.toPersistence(genre);
            
            tnx.begin();
            
            this.em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static GenreRepository getInstance() {
        return GenreRepositoryHolder.INSTANCE;
    }
    
    private static class GenreRepositoryHolder {
        
        final static EntityManager em =
                EntityManagerFactoryHelper.getInstance().getEntityManager();

        private static final GenreRepository INSTANCE =
                new GenreRepository(em);
    }
}
