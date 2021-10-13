/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.CustomEventListenerRegistry;

import org.hibernate.service.spi.*;
import org.hibernate.integrator.spi.*;
import org.hibernate.engine.spi.*;
import org.hibernate.boot.Metadata;
import org.hibernate.event.service.spi.*;
import org.hibernate.event.spi.EventType;
import com.jamapplicationserver.infra.Persistence.database.EntityListeners.DomainEventDispatcher;

/**
 *
 * @author dada
 */
public class CustomEventListenerRegistry implements Integrator {
    
    @Override
    public void integrate(
            Metadata metadata,
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry
    ) {
        
        EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
        eventListenerRegistry.prependListeners(EventType.POST_COMMIT_INSERT, DomainEventDispatcher.class);
        eventListenerRegistry.prependListeners(EventType.POST_COMMIT_UPDATE, DomainEventDispatcher.class);
        eventListenerRegistry.prependListeners(EventType.POST_COMMIT_DELETE, DomainEventDispatcher.class);
        
    }
    
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        
    }
    
}
