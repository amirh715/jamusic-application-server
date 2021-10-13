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
public abstract class DTOWithAuthClaims {
    
    public final UniqueEntityId subjectId;
    public final UserRole subjectRole;
    
    protected DTOWithAuthClaims(UniqueEntityId subjectId, UserRole subjectRole) {
        this.subjectId = subjectId;
        this.subjectRole = subjectRole;
    }
    
}
