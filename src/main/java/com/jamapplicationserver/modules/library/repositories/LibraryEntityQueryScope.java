/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.infra.QueryScope;
import javax.persistence.criteria.*;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.repositories.mappers.*;

/**
 *
 * @author dada
 * @param <T>
 */
public class LibraryEntityQueryScope<T extends LibraryEntity> extends QueryScope<T> {
    
    private final Predicate unpublishedScope =
            this.cb.and(
                    this.cb.isTrue(
                            this.root.get("published")
                    )
            );
    private final List<Predicate> defaultScope;
    
    public LibraryEntityQueryScope(List<Predicate> predicates) {
        super(predicates);
        this.defaultScope = List.of(
                unpublishedScope
        );
    }
    
    public LibraryEntityQueryScope() {
        super();
        this.defaultScope = List.of(
                unpublishedScope
        );
    }
    
    public LibraryEntityQueryScope<T> includeUnpublished() {
        this.defaultScope.remove(
                unpublishedScope
        );
        return this;
    }
    
    public LibraryEntityQueryScope<T> includeUnpublished(PlayerRole role) {
        
        return this;
    }
    
    @Override
    public Set<T> getResults() {
        this.predicates.addAll(this.defaultScope);
        final CriteriaQuery<LibraryEntityModel> query = this.cb
                .createQuery(LibraryEntityModel.class)
                .where(
                        this.cb.and(
                                this.predicates.toArray(
                                        new Predicate[this.predicates.size()]
                                )
                        )
                );
        final List<LibraryEntityModel> results =
                this.manager.createQuery(query).getResultList();
        return (Set<T>)results
                .stream()
                .map(model -> LibraryEntityMapper.toDomain(model))
                .collect(Collectors.toSet());
    }
    
    @Override
    public T getSingleResult() {
        this.predicates.addAll(this.defaultScope);
        final CriteriaQuery<LibraryEntityModel> query = this.cb
                .createQuery(LibraryEntityModel.class)
                .where(
                        this.cb.and(
                                this.predicates.toArray(
                                        new Predicate[this.predicates.size()]
                                )
                        )
                );
        final LibraryEntityModel result =
                this.manager.createQuery(query).getSingleResult();
        return (T)LibraryEntityMapper.toDomain(result);
    }
    
}
