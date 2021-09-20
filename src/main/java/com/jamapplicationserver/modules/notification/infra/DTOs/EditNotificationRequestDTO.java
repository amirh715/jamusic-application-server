/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs;

import java.util.*;
import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.core.infra.*;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author dada
 */
public class EditNotificationRequestDTO implements IDTO {
    
    public final String id;
    public final String type;
    public final String title;
    public final String message;
    public final String route;
    public final String scheduledOn;
    public final boolean sendNow;
    public final Set<String> recipients;
    
    public EditNotificationRequestDTO(
            String id,
            String type,
            String title,
            String message,
            String route,
            String scheduledOn,
            boolean sendNow,
            String recipients
    ) {
        
        final ISerializer serializer = Serializer.getInstance();
        
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.route = route;
        this.scheduledOn = scheduledOn;
        this.sendNow = sendNow;
        this.recipients = serializer.deserialize(recipients, new TypeToken<Set<String>>(){});
        
    }
    
}
