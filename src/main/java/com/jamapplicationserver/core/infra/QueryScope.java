/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import java.util.*;
import javax.persistence.criteria.*;
import javax.persistence.EntityManager;

/**
 *
 * @author dada
 */
public abstract class QueryScope<T> {
    
    protected List<Predicate> predicates;
    protected EntityManager manager;
    protected CriteriaBuilder cb;
    protected CriteriaQuery cq;
    protected Root root;
    protected Class clazz;
    
    protected QueryScope(
            EntityManager manager,
            CriteriaBuilder cb,
            CriteriaQuery cq,
            Root root,
            Class clazz,
            List<Predicate> predicates
    ) {
        this.predicates = new ArrayList<>();
        this.predicates.addAll(predicates);
        this.manager = manager;
        this.cb = cb;
        this.cq = cq;
        this.root = root;
        this.clazz = clazz;
        
    }
    
    protected QueryScope(
            EntityManager manager,
            CriteriaBuilder cb,
            CriteriaQuery cq,
            Root root,
            Class clazz
    ) {
        this.predicates = new ArrayList<>();
        this.manager = manager;
        this.cb = cb;
        this.cq = cq;
        this.root = root;
        this.clazz = clazz;
    }
    
    public abstract Set<T> getResults();
    
    public abstract T getSingleResult();
    
}
