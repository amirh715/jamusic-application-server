/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Notification extends AggregateRoot {
    
    private static final int MAX_ALLOWED_RECIPIENTS = 5000;
    
    private final NotifTitle title;
    
    private final Message message;
    
    private final NotifType type;
    
    private final URL route;
    
    private final boolean isBulk;
    
    private final DateTime createdAt;
    
    private final SenderType senderType;
    
    private final UniqueEntityID sender;
    
    // creation constructor
    private Notification(
            NotifTitle title,
            Message message,
            NotifType type,
            URL route,
            boolean isBulk,
            SenderType senderType,
            UniqueEntityID sender
    ) {
        super();
        this.title = title;
        this.message = message;
        this.type = type;
        this.route = route;
        this.isBulk = isBulk;
        this.senderType = senderType;
        this.sender = sender;
        this.createdAt = DateTime.createNow();
    }
    
    // reconstitution constructor
    private Notification(
            UniqueEntityID id,
            NotifTitle title,
            Message message,
            NotifType type,
            URL route,
            boolean isBulk,
            SenderType senderType,
            UniqueEntityID sender,
            DateTime createdAt
    ) {
        super(id);
        this.title = title;
        this.message = message;
        this.type = type;
        this.route = route;
        this.isBulk = isBulk;
        this.senderType = senderType;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    public static final Result<Notification> create(
            NotifTitle title,
            Message message,
            NotifType type,
            URL route,
            boolean isBulk,
            SenderType senderType,
            UniqueEntityID sender
    ) {
        
        if(title == null)
            return Result.fail(new ValidationError("Notification title is required"));
        
        if(message == null)
            return Result.fail(new ValidationError("Notification message is required"));
        
        final Notification instance = new Notification(
                title,
                message,
                type,
                route,
                isBulk,
                senderType,
                sender
        );
        
        return Result.ok(instance);
    }
    
    public static final Result<Notification> reconstitute(
            UUID id,
            String title,
            String message,
            String type,
            String route,
            boolean isBulk,
            String senderType,
            UUID sender,
            LocalDateTime createdAt
    ) {
        
        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromUUID(id);
        
//        final Notification instance = new Notification();
        
        return Result.ok();
    }
    
}
