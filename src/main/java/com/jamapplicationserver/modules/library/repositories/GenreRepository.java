/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;
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
            
            final String query = "SELECT * FROM GenreModel g";
            
            return (Set<Genre>) this.em.createQuery(query)
                    .getResultList()
                    .stream()
                    .map(model -> GenreMapper.toDomain((GenreModel)model))
                    .collect(Collectors.toSet());

        } catch(Exception e) {
            return null;
        }

    }
    
    @Override
    public Genre fetchByTitle(GenreTitle title) {
        
        try {
            
            final GenreModel model = this.em.find(GenreModel.class, title.getValue());
            
            return GenreMapper.toDomain(model);
            
        } catch(Exception e) {
            return null;
        }

    }

    @Override
    public void save(Genre genre) {
        
        GenreModel model;
        
        em.getTransaction().begin();
        
        if(this.exists(genre.title)) { // updater existing genre
            
            model = new GenreModel();
            
            GenreMapper.mergeForPersistence(genre, model);
            
            em.merge(model);
            
        } else { // insert new genre
            
            model = GenreMapper.toPersistence(genre);
            
            em.persist(model);
            
        }
        
        em.getTransaction().commit();
        
    }
    
    @Override
    public boolean exists(GenreTitle title) {
        
        return true;
    }
    
    @Override
    public void remove(Genre genre) {

        
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
