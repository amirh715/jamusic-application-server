/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RemoveUser;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author dada
 */
public class RemoveUserRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    
    public RemoveUserRequestDTO(
            String id,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        super(updaterId, updaterRole);
        this.id = id;
    }
    
}
