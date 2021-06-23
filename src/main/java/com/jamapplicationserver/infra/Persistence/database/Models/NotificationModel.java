/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import java.util.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.io.Serializable;


/**
 *
 * @author amirhossein
 */
@Entity
@Table(name="notifications", schema="jamschema")
public class NotificationModel implements Serializable {
    
    @Id
    @Column(name="id")
    private UUID id;
    
    @Column(name="title", nullable=true)
    private String title;
    
    @Column(name="message", nullable=false)
    private String message;
    
    @Column(name="type", nullable=false)
    private NotificationTypeEnum type;
    
    @Column(name="route", nullable=true)
    private String route;
    
    @Column(name="is_bulk", nullable=false)
    private boolean isBulk;
    
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    
    @Enumerated
    private NotificationSenderTypeEnum senderType;
    
    @ManyToOne(optional=true)
    private UserModel sentBy;
    
    @ManyToMany
    @JoinTable(
            name="notification_recipients",
            joinColumns = {
                @JoinColumn(name="notification_id", referencedColumnName="id")
            },
            inverseJoinColumns = {
                @JoinColumn(name="recipient_id", referencedColumnName="id")
            }
    )
    private Set<UserModel> recipients;

    
}
