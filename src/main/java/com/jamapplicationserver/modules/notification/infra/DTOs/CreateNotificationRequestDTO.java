/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs;

import java.util.*;
import java.io.InputStream;
import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.core.infra.*;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author dada
 */
public class CreateNotificationRequestDTO implements IDTO {
    
    public final String type;
    public final String title;
    public final String message;
    public final String route;
    public final String senderType;
    public final String scheduledOn;
    public final String senderId;
    public final Set<String> recipients;
    public final InputStream image;
    
    public CreateNotificationRequestDTO(
            String type,
            String title,
            String message,
            String route,
            String senderType,
            String scheduledOn,
            String senderId,
            String recipients,
            InputStream image
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        System.out.println(recipients);
        
        this.type = type;
        this.title = title;
        this.message = message;
        this.route = route;
        this.senderType = senderType;
        this.scheduledOn = scheduledOn;
        this.senderId = senderId;
        this.recipients = serializer.deserialize(recipients, new TypeToken<Set<String>>(){});
        this.image = image;
    }
    
}
