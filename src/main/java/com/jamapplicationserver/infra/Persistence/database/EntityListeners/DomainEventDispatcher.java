/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.EntityListeners;

import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.Models.EntityModel;
import com.jamapplicationserver.core.domain.events.DomainEvents;

/**
 *
 * @author dada
 */
public class DomainEventDispatcher
        implements
        PostInsertEventListener,
        PostUpdateEventListener,
        PostDeleteEventListener {
    
    public DomainEventDispatcher() {
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return true;
    }
    
    @Override
    public void onPostInsert(PostInsertEvent event) {
                
        if(event.getEntity() instanceof EntityModel) {
            dispatchEventsForAggregate((EntityModel) event.getEntity());
        }
        
    }
    
    @Override
    public void onPostUpdate(PostUpdateEvent event) {
                
        if(event.getEntity() instanceof EntityModel) {
            dispatchEventsForAggregate((EntityModel) event.getEntity());
        }
        
    }
    
    @Override
    public void onPostDelete(PostDeleteEvent event) {
                
        if(event.getEntity() instanceof EntityModel) {
            dispatchEventsForAggregate((EntityModel) event.getEntity());
        }
        
    }
    
    private void dispatchEventsForAggregate(EntityModel entity) {
        final UniqueEntityId id =
                UniqueEntityId.createFromUUID(entity.getId()).getValue();
        DomainEvents.dispatchEventsForAggregate(id);
        new Thread(() -> {
            DomainEvents.dispatchEventsForAggregate(id);
        }).start();
    }
    
    public static final DomainEventDispatcher INSTANCE =
            new DomainEventDispatcher();
    
}
