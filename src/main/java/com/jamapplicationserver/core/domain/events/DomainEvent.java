/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain.events;

import java.time.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public abstract class DomainEvent {
    
    public final LocalDateTime occurredAt;
    public final UniqueEntityId aggregateId;
    
    protected DomainEvent(UniqueEntityId aggregateId) {
        this.aggregateId = aggregateId;
        this.occurredAt = LocalDateTime.now();
    }
    
    protected DomainEvent(UniqueEntityId aggregateId, LocalDateTime occurredAt) {
        this.aggregateId = aggregateId;
        this.occurredAt = occurredAt;
    }
    
}
