/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import java.util.*;
import javax.persistence.criteria.*;
import javax.persistence.*;

/**
 *
 * @author dada
 * @param <T>
 */
public abstract class QueryScope<T, U> {
    
    protected final EntityManager entityManager;
    protected final CriteriaBuilder builder;
    protected final CriteriaQuery<T> query;
    protected final Root<T> root;
    protected final List<Predicate> predicates;
    protected final List<Predicate> defaultScope;
    
    protected QueryScope(
            EntityManager entityManager,
            CriteriaQuery query,
            Root root,
            List<Predicate> predicates
    ) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.query = query;
        this.root = root;
        this.predicates = predicates;
        this.defaultScope = new ArrayList<>();
    }
    
    protected QueryScope(
            EntityManager entityManager,
            CriteriaQuery query,
            Root root
    ) {
        this.entityManager = entityManager;
        this.builder = entityManager.getCriteriaBuilder();
        this.query = query;
        this.root = root;
        this.predicates = new ArrayList<>();
        this.defaultScope = new ArrayList<>();
    }
    
    public abstract Set<U> getResults();
    
    public abstract U getSingleResult();
    
}
