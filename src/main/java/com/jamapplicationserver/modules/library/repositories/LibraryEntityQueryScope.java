/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.core.infra.QueryScope;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.NoResultException;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 * @param <T>
 */
public class LibraryEntityQueryScope<T>
        extends QueryScope<T> 
{
    
    private final ILibraryEntityRepository repository;
    private final Predicate unpublishedScope =
        this.cb.isTrue(
                this.root.get("published")
        );
    private final List<Predicate> defaultScope;
    
     public LibraryEntityQueryScope(
            EntityManager manager,
            CriteriaBuilder cb,
            CriteriaQuery cq,
            Root root,
            Class clazz,
            List<Predicate> predicates
    ) {
        super(manager, cb, cq, root, clazz, predicates);
        this.defaultScope = new ArrayList<>();
        this.defaultScope.add(this.unpublishedScope);
        this.predicates.addAll(this.defaultScope);
        this.repository = LibraryEntityRepository.getInstance();
    }

    public LibraryEntityQueryScope(
            EntityManager manager,
            CriteriaBuilder cb,
            CriteriaQuery cq,
            Root root,
            Class clazz
    ) {
        super(manager, cb, cq, root, clazz);
        this.defaultScope = new ArrayList<>();
        this.defaultScope.add(this.unpublishedScope);
        this.predicates.addAll(this.defaultScope);
        this.repository = LibraryEntityRepository.getInstance();
    }
    
    public LibraryEntityQueryScope<T> includeUnpublished() {
        this.predicates.remove(this.unpublishedScope);
        return this;
    }
    
    public LibraryEntityQueryScope<T> includeUnpublished(PlayerRole role) {
        if(role.isAdmin() || role.isLibraryManager())
            this.predicates.remove(this.unpublishedScope);
        return this;
    }
    
    @Override
    public Set<T> getResults() {
        
        this.cq.select(root);
        this.cq.from(this.clazz);
        
        if(!this.predicates.isEmpty()) {
            this.cq.where(
                    this.cb.and(
                            this.predicates.toArray(
                                    new Predicate[this.predicates.size()]
                            )
                    )
            );
        }
        
        final List<LibraryEntityModel> results =
                this.manager.createQuery(this.cq).getResultList();
        return (Set<T>) results
                .stream()
                .map(model -> LibraryEntityRepository.toDomain(model, manager))
                .collect(Collectors.toSet());
    }
    
    @Override
    public T getSingleResult() {
        
        try {
            
            this.cq.select(root);
            this.cq.from(this.clazz);

            if(!this.predicates.isEmpty()) {
                this.cq.where(
                        this.cb.and(
                                this.predicates.toArray(
                                        new Predicate[this.predicates.size()]
                                )
                        )
                );
            }

            final LibraryEntityModel result =
                    (LibraryEntityModel) manager.createQuery(this.cq).getSingleResult();
            
            return (T) LibraryEntityRepository.toDomain(result, manager);
            
        } catch(NoResultException e) {
            e.printStackTrace();
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    
}
