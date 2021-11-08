/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.util.*;
import java.io.*;
import org.hibernate.envers.*;

/**
 *
 * @author dada
 */
@MappedSuperclass
@Audited
public class EntityModel implements Serializable {
    
    @Id
    @Column(name="id", updatable=false)
    protected UUID id;
    
    public EntityModel() {
        
    }
    
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof EntityModel))
            return false;
        EntityModel entity = (EntityModel) obj;
        return entity.id.equals(this.id);
    }
    
}
