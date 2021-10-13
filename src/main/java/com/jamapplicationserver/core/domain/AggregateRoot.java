/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.util.*;
import com.jamapplicationserver.core.domain.events.*;

/**
 *
 * @author amirhossein
 * 
 */
public abstract class AggregateRoot extends Entity {
    
    private Set<DomainEvent> domainEvents = new HashSet<>();
        
    public AggregateRoot(
            UniqueEntityId id,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        super(id, createdAt, lastModifiedAt);
    }
    
    public AggregateRoot() {
        super();
    }
    
    public void addDomainEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
        DomainEvents.markAggregateForDispatch(this);
    }
    
    public final Set<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
    
    public final void clearEvents() {
        domainEvents = new HashSet<>();
    }
    
    @Override
    public String toString() {
        return this.id.toString();
    }
    
}
