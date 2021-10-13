/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain.events;

/**
 *
 * @author dada
 * @param <T>
 */
public interface IDomainEventHandler<T extends DomainEvent> {
    
    void handleEvent(T domainEvent) throws Exception;
    
    Class<T> subscribedToEventType();
    
}
