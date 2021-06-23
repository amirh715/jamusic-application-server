/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.util.List;
import com.jamapplicationserver.core.domain.events.DomainEvent;
import com.jamapplicationserver.core.domain.events.DomainEvents;

/**
 *
 * @author amirhossein
 * 
 */
public abstract class AggregateRoot extends Entity {
    
    private List<DomainEvent> domainEvents;
    
    public AggregateRoot(UniqueEntityID id) {
        super(id);
    }
    
    public AggregateRoot() {
        super();
    }
    
    public List<DomainEvent> getDomainEvents() {
        return this.domainEvents;
    }

    public UniqueEntityID getID() {
        return this.id;
    }
    
    protected void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
        DomainEvents.markAggregateForDispatch(this);
    }
    
    public void clearEvents() {
        this.domainEvents.clear();
    }
    
    @Override
    public String toString() {
        return this.id.toString();
    }
    
}
