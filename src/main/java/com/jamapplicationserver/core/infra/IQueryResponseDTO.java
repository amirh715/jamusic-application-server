/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public interface IQueryResponseDTO {
    
    /**
     * Filters response data transfer objects fields based on user role
     * @param role
     * @return IQueryResponseDTO
     */
    public IQueryResponseDTO filter(UserRole role);
    
}
