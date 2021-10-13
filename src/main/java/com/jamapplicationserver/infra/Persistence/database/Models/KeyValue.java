/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author dada
 */
@Entity
@Table(name="key_value_store", schema="jamschema")
public class KeyValue implements Serializable {
    
    @Id
    @Column(name="keyy")
    private String key;
    
    @Column(name="valuee")
    private String value;
    
    public KeyValue() {
        
    }
    
    public String getKey() {
        return this.key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
