/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain.events;

import java.time.LocalDateTime;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public abstract class DomainEvent {
    
    public final LocalDateTime occurredAt;
    public final AggregateRoot aggregate;
    
    public DomainEvent(
            LocalDateTime occurredAt,
            AggregateRoot aggregate
    ) {
        this.occurredAt = occurredAt;
        this.aggregate = aggregate;
    }
    
    public DomainEvent(AggregateRoot aggregate) {
        this.occurredAt = LocalDateTime.now();
        this.aggregate = aggregate;
    }
    
    public UniqueEntityID getAggregateId() {
        return this.aggregate.id;
    }
    
}
