/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.infra.services;

import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.showcase.infra.DTOs.queries.ShowcaseDetails;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;

/**
 *
 * @author dada
 */
public class ShowcaseQueryService implements IShowcaseQueryService {
    
    private final EntityManagerFactoryHelper emfh;
    
    private ShowcaseQueryService(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    @Override
    public Set<ShowcaseDetails> getAllShowcases() {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "SELECT showcases FROM ShowcaseModel showcases";
            
            return em.createQuery(query, ShowcaseModel.class)
                    .getResultStream()
                    .map(showcase -> {
                        return new ShowcaseDetails(
                                showcase.getId(),
                                showcase.getIndex(),
                                showcase.getTitle(),
                                showcase.getDescription(),
                                showcase.getRoute(),
                                showcase.getInteractionCount(),
                                showcase.getCreatedAt(),
                                showcase.getLastModifiedAt()
                        );
                    })
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public ShowcaseDetails getShowcaseById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final ShowcaseModel showcase =
                    em.find(ShowcaseModel.class, id.toValue());
            
            return new ShowcaseDetails(
                    showcase.getId(),
                    showcase.getIndex(),
                    showcase.getTitle(),
                    showcase.getDescription(),
                    showcase.getRoute(),
                    showcase.getInteractionCount(),
                    showcase.getCreatedAt(),
                    showcase.getLastModifiedAt()
            );
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    public static ShowcaseQueryService getInstance() {
        return ShowcaseQueryServiceHolder.INSTANCE;
    }
    
    private static class ShowcaseQueryServiceHolder {

        private static final ShowcaseQueryService INSTANCE =
                new ShowcaseQueryService(EntityManagerFactoryHelper.getInstance());
    }
}
