/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class RemoveNotificationRequestDTO implements IDTO {
    
    public final String id;
    
    public RemoveNotificationRequestDTO(String id) {
        this.id = id;
    }
    
}
