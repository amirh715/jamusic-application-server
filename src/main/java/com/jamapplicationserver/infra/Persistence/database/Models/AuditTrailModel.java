/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.UUID;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="audit", schema="jamschema")
public class AuditTrailModel implements Serializable {

    @Id
    @Column(name="id")
    private UUID id;
    
    @Enumerated
    private AuditActionEnum action;
    
    @Column(name="audited_at")
    private LocalDateTime auditedAt;
    
    @Column(name="table_name")
    private String tableName;
    
    @Column(name="note")
    private String note;
    
    @Column(name="record")
    private String record;
    
    public AuditTrailModel() {
        
    }
    
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public AuditActionEnum getAction() {
        return this.action;
    }
    
    public void setAction(AuditActionEnum action) {
        this.action = action;
    }
    
    public LocalDateTime getAuditedAt() {
        return this.auditedAt;
    }
    
    public void setAuditedAt(LocalDateTime auditedAt) {
        this.auditedAt = auditedAt;
    }
    
    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public String getRecord() {
        return this.record;
    }
    
    public void setRecord(String record) {
        this.record = record;
    }
    
}
