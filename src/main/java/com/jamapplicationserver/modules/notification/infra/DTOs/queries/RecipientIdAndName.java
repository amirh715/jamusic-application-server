/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.DTOs.queries;

import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class RecipientIdAndName implements IQueryResponseDTO {
    
    public String id;
    public String name;
    
    public RecipientIdAndName(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    @Override
    public RecipientIdAndName filter(UserRole role) {
        
        
        return this;
    }
    
}
