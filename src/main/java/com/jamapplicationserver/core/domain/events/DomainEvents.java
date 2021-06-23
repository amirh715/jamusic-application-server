/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain.events;

import java.util.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public class DomainEvents {
    
    private static final Set<AggregateRoot> markedAggregates = new HashSet<>();
    private static final Map<Class, Set<IHandle>> handlersMap = new HashMap<>();
    
    public static void markAggregateForDispatch(AggregateRoot aggregate) {
        DomainEvents.markedAggregates.add(aggregate);
    }
    
    public static void dispatchAggregateEvents(AggregateRoot aggregate) {
        aggregate
                .getDomainEvents()
                .forEach(event -> DomainEvents.dispatch(event));
    }
    
    public static void removeAggregateFromMarkedDispatchList(AggregateRoot aggregate) {
        markedAggregates.remove(aggregate);
    }
    
    public static AggregateRoot findMarkedAggregateById(UniqueEntityID id) {
        AggregateRoot found = null;
        for(AggregateRoot aggregate : markedAggregates)
            if(aggregate.id.equals(id)) found = aggregate;
        return found;
    }
    
    public static void dispatchEventsForAggregate(UniqueEntityID id) {
        final AggregateRoot aggregate = findMarkedAggregateById(id);
        if(aggregate != null) {
            dispatchAggregateEvents(aggregate);
            aggregate.clearEvents();
            removeAggregateFromMarkedDispatchList(aggregate);
        }
    }
    
    public static void register(
            IHandle handler,
            Class eventClass
    ) {
        
        if(!handlersMap.containsKey(eventClass))
          handlersMap.get(eventClass).clear();
        handlersMap.get(eventClass).add(handler);
        
    }
    
    public static void clearHandlers() {
        handlersMap.clear();
    }
    
    public static void clearMarkedAggregates() {
        markedAggregates.clear();
    }
    
    private static void dispatch(DomainEvent event) {
        
        final Class eventClass = event.getClass();
        
        if(handlersMap.containsKey(eventClass)) {
            final Set<IHandle> handlers = handlersMap.get(eventClass);
            handlers.forEach(handler -> handler.handle(event));
        }
        
    }
    
}
