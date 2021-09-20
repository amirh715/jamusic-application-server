/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.CreateUser;

import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public class CreateUserResponseDTO implements IDTO {
    
    public final UniqueEntityId id;
    
    public CreateUserResponseDTO(
            UniqueEntityId id
    ) {
        this.id = id;
    }
    
}
