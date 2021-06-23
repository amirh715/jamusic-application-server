/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import java.util.*;
import javax.persistence.criteria.*;
import javax.persistence.EntityManager;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;

/**
 *
 * @author dada
 */
public abstract class QueryScope<T> {
    
    protected List<Predicate> predicates;
    protected EntityManager manager;
    protected CriteriaBuilder cb;
    protected Root<LibraryEntityModel> root;
    
    public QueryScope(List<Predicate> predicates) {
        this.predicates = predicates;
        this.manager = EntityManagerFactoryHelper
                .getInstance()
                .getEntityManager();
        this.cb = this.manager.getCriteriaBuilder();
        this.root = this.cb.createQuery().from(LibraryEntityModel.class);
    }
    
    public QueryScope() {
        this.predicates = List.of();
        this.manager = EntityManagerFactoryHelper
                .getInstance()
                .getEntityManager();
        this.cb = this.manager.getCriteriaBuilder();
        this.root = this.cb.createQuery().from(LibraryEntityModel.class);
    }
    
    public abstract Set<T> getResults();
    
    public abstract T getSingleResult();
    
}
