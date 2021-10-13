/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ActivateBlockUser;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author amirhossein
 */
public class ActivateBlockUserRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    public final String state;
    
    public ActivateBlockUserRequestDTO(
            String id,
            String state,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        super(updaterId, updaterRole);
        this.id = id;
        this.state = state;
    }
    
}
