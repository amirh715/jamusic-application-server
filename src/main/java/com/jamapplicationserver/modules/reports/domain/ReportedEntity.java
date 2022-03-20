/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain;

import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class ReportedEntity extends ValueObject {
    
    private final UniqueEntityId id;
    private final ReportedEntityTitle title;
    private final UniqueEntityId creatorId;
    
    @Override
    public String getValue() {
        return this.toString();
    }
    
    public ReportedEntity(
            UniqueEntityId id,
            ReportedEntityTitle title,
            UniqueEntityId creatorId
    ) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
    }
    
    public final UniqueEntityId getId() {
        return this.id;
    }

    public final ReportedEntityTitle getTitle() {
        return this.title;
    }
    
    public final UniqueEntityId getCreatorId() {
        return this.creatorId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof ReportedEntity))
            return false;
        ReportedEntity re = (ReportedEntity) obj;
        return re.id.equals(re);
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
}
