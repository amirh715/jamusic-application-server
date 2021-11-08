/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.DTOs.queries;

import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;

/**
 *
 * @author dada
 */
public class AuthToken implements IQueryResponseDTO {
    
    public String token;
    
    public AuthToken(String token) {
        this.token = token;
    }
    
    @Override
    public AuthToken filter(UserRole role) {
        return this;
    }
    
}
