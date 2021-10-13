/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.core.infra.QueryScope;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author dada
 * @param <T>
 */
public class LibraryEntityQueryScope<T extends LibraryEntity>
        extends QueryScope<LibraryEntityModel, T> 
{

    private final Predicate publishedScope = builder.isTrue(root.get("published"));
    
    public LibraryEntityQueryScope(
            EntityManager entityManager,
            CriteriaQuery query,
            Root root,
            List<Predicate> predicates
    ) {
        super(entityManager, query, root, predicates);
        defaultScope.add(publishedScope);
    }
    
    private LibraryEntityQueryScope<T> includeUnpublished() {
        defaultScope.remove(publishedScope);
        return this;
    }
    
    public LibraryEntityQueryScope<T> includeUnpublished(UserRole role) {
        if(role.isAdmin() || role.isLibraryManager())
            includeUnpublished();
        return this;
    }

    @Override
    public Set<T> getResults() {
        
        final ILibraryEntityRepository libraryRepository =
                LibraryEntityRepository.getInstance();

        try {
            
            query.where(
                    builder.and(
                            predicates.toArray(new Predicate[predicates.size()])
                    )
            );
            
            return entityManager
                    .createQuery(query)
                    .getResultList()
                    .stream()
                    .map(entity ->
                            (T) libraryRepository.toDomain(
                                    (LibraryEntityModel) entity,
                                    entityManager
                            )
                    )
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        
    }
    
    @Override
    public T getSingleResult() {
        
        final ILibraryEntityRepository libraryRepository =
                LibraryEntityRepository.getInstance();
        
        try {
            
            query.where(
                    builder.and(
                            predicates.toArray(new Predicate[predicates.size()])
                    )
            );
            
            final LibraryEntityModel result = entityManager.createQuery(query).getSingleResult();
            
            return (T) libraryRepository.toDomain(
                    result,
                    entityManager
            );
            
        } catch(Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }
    
}
