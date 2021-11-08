/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain.events;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class DomainEvents {
    
    private static List<IDomainEventHandler> handlers =
            new ArrayList<>();
    private static ThreadLocal<List<AggregateRoot>> markedAggregates =
            new ThreadLocal<List<AggregateRoot>>();
    
    public static final void markAggregateForDispatch(AggregateRoot aggregate) {
        if(markedAggregates.get() == null) {
            markedAggregates.set(new ArrayList<>());
        }
        markedAggregates.get().add(aggregate);
    }
    
    private static AggregateRoot findMarkedAggregateById(UniqueEntityId id) {
        AggregateRoot found = null;
        if(markedAggregates.get() != null && !markedAggregates.get().isEmpty()) {
            for(AggregateRoot aggregate : markedAggregates.get()) {
                if(aggregate.getId().equals(id)) found = aggregate;
            }
        }
        
        return found;
    }
    
    public static final void dispatchEventsForAggregate(UniqueEntityId aggregateId) {
        final AggregateRoot aggregate = findMarkedAggregateById(aggregateId);
        if(aggregate != null) {
            dispatchAggregateEvents(aggregate);
            aggregate.clearEvents();
            markedAggregates.get().remove(aggregate);
        }

    }
    
    private static void dispatchAggregateEvents(AggregateRoot aggregate) {
        aggregate.getDomainEvents()
            .forEach(event -> dispatch(event));
    }
    
    private static void dispatch(DomainEvent domainEvent) {
        try {
            
            if(handlers != null) {
                for(IDomainEventHandler handler : handlers) {
                    final Class subscribedTo = handler.subscribedToEventType();
                    if (subscribedTo == domainEvent.getClass()) {
                        handler.handleEvent(domainEvent);
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
            // LOG
        }
        
    }
    
    public static final <T extends DomainEvent> void register(IDomainEventHandler handler) {
        if(handlers == null) {
            handlers = new ArrayList<>();
        }
        handlers.add(handler);
        LogService.getInstance().log("Domain event handler (" + handler.getClass() + ") registered ");
    }
    
    public static void clearMarkedAggregates() {
        markedAggregates.set(new ArrayList<>());
    }
    
}
